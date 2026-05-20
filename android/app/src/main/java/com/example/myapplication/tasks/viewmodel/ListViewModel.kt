package com.example.myapplication.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.tasks.repository.TaskRepository
import com.example.myapplication.tasks.ui.state.TaskListUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    // Satisfies requirement #3: Using reactive Flow from DAO in the app
    val uiState: StateFlow<TaskListUiState> = repository.tasks
        .map { tasks ->
            if (tasks.isEmpty()) TaskListUiState.Empty
            else TaskListUiState.Success(tasks)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TaskListUiState.Loading
        )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            repository.refreshTasks()
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            repository.deleteTask(id)
        }
    }
}