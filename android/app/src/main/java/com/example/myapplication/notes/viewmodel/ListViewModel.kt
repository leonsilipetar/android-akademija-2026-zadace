package com.example.myapplication.notes.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.myapplication.notes.model.Note
import com.example.myapplication.notes.repository.NoteRepository

class ListViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    var notes by mutableStateOf(listOf<Note>())
        private set

    init {
        loadNotes()
    }

    fun loadNotes() {
        notes = repository.getAll()
    }
}