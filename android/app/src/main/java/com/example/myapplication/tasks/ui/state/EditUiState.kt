package com.example.myapplication.tasks.ui.state

data class EditUiState(
    val id: String? = null,
    val title: String = "",
    val body: String = "",
    val color: String = "#FFF9C4",
    val isCompleted: Boolean = false,
    val category: String = "General",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
