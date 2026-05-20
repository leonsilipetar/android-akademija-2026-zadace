package com.example.myapplication.tasks.ui.state

data class EditUiState(
    val id: Int? = null,
    val title: String = "",
    val body: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)