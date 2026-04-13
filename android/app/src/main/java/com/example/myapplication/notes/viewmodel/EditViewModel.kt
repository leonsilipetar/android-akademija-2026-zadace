package com.example.myapplication.notes.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.myapplication.notes.model.Note
import com.example.myapplication.notes.repository.NoteRepository

class EditViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var createdAt: Long = System.currentTimeMillis()

    private var currentId: Int = -1

    fun loadNote(id: Int) {
        if (id == -1) {
            currentId = -1
            title = ""
            description = ""
            createdAt = System.currentTimeMillis()
            return
        }

        val note = repository.getById(id)
        note?.let {
            currentId = it.id
            title = it.title
            description = it.description
            createdAt = it.createdAt
        }
    }

    fun save() {
        if (currentId == -1) {
            val newNote = Note(
                id = (0..100000).random(),
                title = title,
                description = description,
                createdAt = createdAt
            )
            repository.add(newNote)
        } else {
            val updated = Note(
                id = currentId,
                title = title,
                description = description,
                createdAt = createdAt
            )
            repository.update(updated)
        }
    }
}