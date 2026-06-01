package com.example.myapplication.tasks.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.example.myapplication.tasks.ui.state.BackgroundPreset
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    authRepository: AuthRepository,
    taskRepository: TaskRepository
) {
    val navController = rememberNavController()
    val tokenState = authRepository.tokenFlow.collectAsState(initial = null)
    val token = tokenState.value

    val usernameState = authRepository.usernameFlow.collectAsState(initial = null)
    val username = usernameState.value

    val backgroundIdState = authRepository.backgroundIdFlow.collectAsState(initial = "default")
    val backgroundPreset = BackgroundPreset.getById(backgroundIdState.value)

    val scope = rememberCoroutineScope()
    val startDestination = if (token == null) "login" else "list"

    LaunchedEffect(token) {
        SessionManager.token = token
    }

    Scaffold(
        containerColor = Color.Transparent
    ) { innerPadding ->
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

                AppBackgroundWrapper(backgroundPreset = backgroundPreset) {
                    when (uiState) {
                        is TaskListUiState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Loading...")
                            }
                        }

                        is TaskListUiState.Empty, is TaskListUiState.Success -> {
                            val tasks = if (uiState is TaskListUiState.Success) uiState.tasks else emptyList()
                            TaskListScreen(
                                tasks = tasks,
                                username = username,
                                onAddClick = { navController.navigate("detail/-1") },
                                isDark = backgroundPreset.isDark,
                                onNoteClick = { id -> navController.navigate("detail/$id") },
                                onDeleteConfirm = { id -> listViewModel.deleteTask(id) },
                                onRandomPick = { id -> navController.navigate("detail/$id") },
                                onLogout = {
                                    scope.launch {
                                        authRepository.logout()
                                        navController.navigate("login") {
                                            popUpTo("list") { inclusive = true }
                                        }
                                    }
                                },
                                onBackgroundChange = { newId ->
                                    scope.launch {
                                        authRepository.setBackground(newId)
                                    }
                                }
                            )
                        }

                        is TaskListUiState.Error -> {
                            Text(uiState.message)
                        }
                    }
                }
            }

            composable(
                route = "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: "-1"

                val editViewModel: EditViewModel = viewModel(
                    factory = EditViewModelFactory(taskRepository, taskId = id)
                )

                LaunchedEffect(id) {
                    editViewModel.loadTask(id)
                }

                AppBackgroundWrapper(backgroundPreset = backgroundPreset) {
                    TaskDetailScreen(
                        viewModel = editViewModel,
                        onBack = { navController.popBackStack() },
                        onSaveDone = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun AppBackgroundWrapper(
    backgroundPreset: BackgroundPreset,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (backgroundPreset.imageResId != null) {
            Image(
                painter = painterResource(id = backgroundPreset.imageResId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            if (backgroundPreset.secondaryColor != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(backgroundPreset.primaryColor, backgroundPreset.secondaryColor)
                            )
                        )
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundPreset.primaryColor)
                )
            }
        }
        content()
    }
}
