package com.example.myapplication.tasks.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.tasks.viewmodel.EditViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    viewModel: EditViewModel,
    onBack: () -> Unit,
    onSaveDone: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    
    // Deterministic color logic to match TaskListScreen
    val colors = listOf(
        Color(0xFFFFF9C4), Color(0xFFFFECB3), Color(0xFFFFCCBC), Color(0xFFF8BBD0),
        Color(0xFFE1BEE7), Color(0xFFD1C4E9), Color(0xFFC5CAE9), Color(0xFFBBDEFB),
        Color(0xFFB2EBF2), Color(0xFFB2DFDB), Color(0xFFC8E6C9), Color(0xFFDCEDC8)
    )
    
    val backgroundColor = if (state.id != null && state.id != -1) {
        val colorIndex = java.util.Random(state.id!!.toLong()).nextInt(colors.size)
        colors[colorIndex]
    } else {
        Color.White
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.id == -1) "New Task" else "Edit Task") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.save(onSuccess = onSaveDone)
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { padding ->
        val errorMessage = state.errorMessage
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            OutlinedTextField(
                value = state.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = state.body,
                onValueChange = viewModel::onBodyChange,
                label = { Text("Body") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .weight(1f),
                minLines = 5
            )
        }
    }
}