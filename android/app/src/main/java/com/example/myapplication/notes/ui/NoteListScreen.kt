package com.example.myapplication.notes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.notes.model.Note
import com.example.myapplication.tasks.components.CustomButton
import com.example.myapplication.tasks.components.TitleText

@Composable
fun NoteListScreen(
    notes: List<Note>,
    onAddClick: () -> Unit,
    onNoteClick: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            TitleText("Notes")
            CustomButton("+ Add") {
                onAddClick()
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(notes) { note ->
                NoteCard(data = note, onClick = { onNoteClick(note.id) })
            }
        }
    }
}