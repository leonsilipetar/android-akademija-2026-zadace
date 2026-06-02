package com.example.myapplication.tasks.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.tasks.data.remote.dto.TaskRequest
import com.example.myapplication.tasks.repository.TaskRepository
import com.example.myapplication.tasks.ui.state.EditUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditViewModel(
    private val repository: TaskRepository,
    private val taskId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditUiState(id = if (taskId == "-1") null else taskId))
    val uiState = _uiState.asStateFlow()

    fun loadTask(id: String) {
        Log.d("EditViewModel", "Loading task with id: $id")
        if (id == "-1") {
            _uiState.value = EditUiState(id = null, title = "", body = "")
            return
        }

        viewModelScope.launch {
           repository.tasks.value.find { it.id == id }?.let { localTask ->
                Log.d("EditViewModel", "Found task in local cache: ${localTask.title}")
                _uiState.value = EditUiState(
                    id = id,
                    title = localTask.title,
                    body = localTask.body,
                    color = localTask.color,
                    isCompleted = localTask.isCompleted,
                    category = localTask.category
                )
            }

            repository.getTaskById(id)
                .onSuccess { task ->
                    Log.d("EditViewModel", "Successfully loaded task from API: ${task.title}")
                    _uiState.value = EditUiState(
                        id = id,
                        title = task.title,
                        body = task.body,
                        color = task.color,
                        isCompleted = task.isCompleted,
                        category = task.category
                    )
                }
                .onFailure { error ->
                    Log.e("EditViewModel", "Failed to load task from API", error)
                }
        }
    }

    fun onTitleChange(value: String) {
        _uiState.value = _uiState.value.copy(title = value)
    }

    fun onBodyChange(value: String) {
        _uiState.value = _uiState.value.copy(body = value)
    }

    fun onColorChange(value: String) {
        _uiState.value = _uiState.value.copy(color = value)
    }

    fun onCategoryChange(value: String) {
        _uiState.value = _uiState.value.copy(category = value)
    }

    fun onCompletionChange(value: Boolean) {
        _uiState.value = _uiState.value.copy(isCompleted = value)
    }

    fun save(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val currentState = _uiState.value
            val request = TaskRequest(currentState.title, currentState.body)

            val result = if (currentState.id == null) {
                repository.createTask(request, currentState.color, currentState.category).onSuccess { newId ->
                    _uiState.value = currentState.copy(id = newId, isLoading = false)
                    onSuccess()
                }
            } else {
                repository.updateTask(
                    id = currentState.id,
                    title = currentState.title,
                    body = currentState.body,
                    color = currentState.color,
                    isCompleted = currentState.isCompleted,
                    category = currentState.category
                ).onSuccess {
                    _uiState.value = currentState.copy(isLoading = false)
                    onSuccess()
                }
            }

            result.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to save task: ${error.message}"
                )
            }
        }
    }
}
