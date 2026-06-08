package com.test.memoboard.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.memoboard.tasks.repository.TaskRepository

class EditViewModelFactory(
    private val repository: TaskRepository,
    private val taskId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditViewModel(repository, taskId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
