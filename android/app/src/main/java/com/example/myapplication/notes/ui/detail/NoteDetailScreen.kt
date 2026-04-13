package com.example.myapplication.notes.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import com.example.myapplication.notes.utils.formatDate
import com.example.myapplication.notes.viewmodel.EditViewModel
import com.example.myapplication.tasks.components.CustomButton
@Composable

fun NoteDetailScreen(
    viewModel: EditViewModel,
    onBack: () -> Unit,
    onSaveDone: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomButton("<- Back") {
                onBack()
            }

            CustomButton("Save") {
                viewModel.save()
                onSaveDone()
            }
        }

        Text(text = formatDate(viewModel.createdAt))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.title,
            onValueChange = { viewModel.title = it },
            label = { Text("Title") }
        )

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = viewModel.description,
            onValueChange = { viewModel.description = it },
            label = { Text("Description") }
        )
    }
}