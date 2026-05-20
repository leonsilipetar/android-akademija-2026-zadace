package com.example.myapplication.tasks.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.tasks.data.remote.SessionManager
import com.example.myapplication.tasks.repository.AuthRepository
import com.example.myapplication.tasks.repository.TaskRepository
import com.example.myapplication.tasks.ui.auth.LoginScreen
import com.example.myapplication.tasks.ui.detail.TaskDetailScreen
import com.example.myapplication.tasks.ui.list.TaskListScreen
import com.example.myapplication.tasks.ui.state.TaskListUiState
import com.example.myapplication.tasks.viewmodel.EditViewModel
import com.example.myapplication.tasks.viewmodel.EditViewModelFactory
import com.example.myapplication.tasks.viewmodel.ListViewModel
import com.example.myapplication.tasks.viewmodel.ListViewModelFactory
import com.example.myapplication.tasks.viewmodel.LoginViewModel
import com.example.myapplication.tasks.viewmodel.LoginViewModelFactory

@Composable
fun AppNavigation(
    authRepository: AuthRepository,
    taskRepository: TaskRepository
) {
    val navController = rememberNavController()
    val tokenState = authRepository.tokenFlow.collectAsState(initial = null)
    val token = tokenState.value

    // Determine start destination based on token presence
    val startDestination = if (token == null) "login" else "list"

    // Side effect to sync SessionManager with the persisted token
    LaunchedEffect(token) {
        SessionManager.token = token
    }

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                val loginViewModel: LoginViewModel = viewModel(
                    factory = LoginViewModelFactory(authRepository)
                )

                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = {
                        navController.navigate("list") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }

            composable("list") {
                val listViewModel: ListViewModel = viewModel(
                    factory = ListViewModelFactory(taskRepository)
                )

                val uiState = listViewModel.uiState.collectAsState().value

                when (uiState) {
                    is TaskListUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Loading...")
                        }
                    }

                    is TaskListUiState.Empty -> {
                        TaskListScreen(
                            tasks = emptyList(),
                            onAddClick = { navController.navigate("detail/-1") },
                            onNoteClick = { id -> navController.navigate("detail/$id") },
                            onDeleteConfirm = { id -> listViewModel.deleteTask(id) },
                            onRandomPick = { id -> navController.navigate("detail/$id") }
                        )
                    }

                    is TaskListUiState.Error -> {
                        Text(uiState.message)
                    }

                    is TaskListUiState.Success -> {
                        TaskListScreen(
                            tasks = uiState.tasks,
                            onAddClick = { navController.navigate("detail/-1") },
                            onNoteClick = { id -> navController.navigate("detail/$id") },
                            onDeleteConfirm = { id -> listViewModel.deleteTask(id) },
                            onRandomPick = { id -> navController.navigate("detail/$id") }
                        )
                    }
                }
            }

            composable(
                route = "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toInt() ?: -1

                val editViewModel: EditViewModel = viewModel(
                    factory = EditViewModelFactory(taskRepository, taskId = id)
                )

                LaunchedEffect(id) {
                    editViewModel.loadTask(id)
                }

                TaskDetailScreen(
                    viewModel = editViewModel,
                    onBack = { navController.popBackStack() },
                    onSaveDone = { navController.popBackStack() }
                )
            }
        }
    }
}