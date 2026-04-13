package com.example.myapplication.notes.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.myapplication.notes.model.Note
import com.example.myapplication.notes.repository.NoteRepository
import com.example.myapplication.notes.ui.detail.NoteDetailScreen
import com.example.myapplication.notes.ui.list.NoteListScreen
import com.example.myapplication.notes.viewmodel.EditViewModel
import com.example.myapplication.notes.viewmodel.ListViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val repository by lazy { NoteRepository() }

    val listViewModel = remember { ListViewModel(repository) }
    val editViewModel = remember { EditViewModel(repository) }

    Scaffold { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "list",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("list") {
                NoteListScreen(
                    notes = listViewModel.notes,
                    onAddClick = {
                        navController.navigate("detail/-1")
                    },
                    onNoteClick = { id ->
                        navController.navigate("detail/$id")
                    }
                )
            }

            composable("detail/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toInt() ?: -1

                LaunchedEffect(id) {
                    editViewModel.loadNote(id)
                }

                NoteDetailScreen(
                    viewModel = editViewModel,

                    onBack = {
                        navController.popBackStack()
                    },

                    onSaveDone = {
                        listViewModel.loadNotes()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}