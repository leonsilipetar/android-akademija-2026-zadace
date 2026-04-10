package com.example.myapplication.notes.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.myapplication.notes.model.Note
import com.example.myapplication.notes.ui.NoteDetailScreen
import com.example.myapplication.notes.ui.NoteListScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    var notes by remember {
        mutableStateOf(
            listOf(
                Note(1, "Note 1", "Opis 1"),
                Note(2, "Note 2", "Opis 2")
            )
        )
    }

    Scaffold { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "list",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("list") {
                NoteListScreen(
                    notes = notes,
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

                NoteDetailScreen(
                    note = notes.find { it.id == id },

                    onSave = { title, description ->

                        if (id == -1) {
                            val newNote = Note(
                                id = notes.size + 1,
                                title = title,
                                description = description
                            )
                            notes = notes + newNote
                        } else {
                            notes = notes.map {
                                if (it.id == id)
                                    it.copy(title = title, description = description)
                                else it
                            }
                        }

                        navController.popBackStack()
                    },

                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}