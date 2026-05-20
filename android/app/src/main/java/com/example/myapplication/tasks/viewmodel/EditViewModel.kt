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
    private val taskId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditUiState(id = taskId))
    val uiState = _uiState.asStateFlow()

    fun loadTask(id: Int) {
        Log.d("EditViewModel", "Loading task with id: $id")
        if (id == -1) {
            _uiState.value = EditUiState(id = -1, title = "", body = "")
            return
        }

        viewModelScope.launch {
           repository.tasks.value.find { it.id == id }?.let { localTask ->
                Log.d("EditViewModel", "Found task in local cache: ${localTask.title}")
                _uiState.value = EditUiState(
                    id = id,
                    title = localTask.title,
                    body = localTask.body
                )
            }

            repository.getTaskById(id)
                .onSuccess { task ->
                    Log.d("EditViewModel", "Successfully loaded task from API: ${task.title}")
                    _uiState.value = EditUiState(
                        id = id,
                        title = task.title,
                        body = task.body
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

    fun save(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val currentState = _uiState.value
            val request = TaskRequest(currentState.title, currentState.body)

            val result = if (currentState.id == -1) {
                repository.createTask(request).onSuccess { newId ->
                    _uiState.value = currentState.copy(id = newId, isLoading = false)
                    onSuccess()
                }
            } else if (currentState.id != null) {
                repository.updateTask(currentState.id, currentState.title, currentState.body).onSuccess {
                    _uiState.value = currentState.copy(isLoading = false)
                    onSuccess()
                }
            } else {
                Result.failure(Exception("Unknown ID"))
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