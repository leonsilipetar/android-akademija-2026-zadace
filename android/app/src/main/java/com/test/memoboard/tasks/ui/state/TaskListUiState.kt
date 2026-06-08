package com.test.memoboard.tasks.ui.state

import com.test.memoboard.tasks.model.Task

sealed class TaskListUiState {
    data object Loading : TaskListUiState()
    data object Empty : TaskListUiState()
    data class Success(val tasks: List<Task>) : TaskListUiState()
    data class Error(val message: String) : TaskListUiState()
}
