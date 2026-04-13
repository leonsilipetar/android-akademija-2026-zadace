package com.example.myapplication.notes.repository

import com.example.myapplication.notes.model.Note

class NoteRepository {

    private val notes = mutableListOf(
        Note(1, "Note 1", "Opis 1", System.currentTimeMillis()),
        Note(2, "Note 2", "Opis 2", System.currentTimeMillis())
    )

    fun getAll(): List<Note> = notes

    fun getById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    fun add(note: Note) {
        notes.add(note)
    }

    fun update(updatedNote: Note) {
        val index = notes.indexOfFirst { it.id == updatedNote.id }
        if (index != -1) {
            notes[index] = updatedNote
        }
    }
}