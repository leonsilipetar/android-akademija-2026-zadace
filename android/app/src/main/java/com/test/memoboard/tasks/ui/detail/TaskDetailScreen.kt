package com.test.memoboard.tasks.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.test.memoboard.tasks.viewmodel.EditViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    viewModel: EditViewModel,
    onBack: () -> Unit,
    onSaveDone: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val backgroundColor = try {
        Color(android.graphics.Color.parseColor(state.color))
    } catch (e: Exception) {
        Color.White
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.id == null) "New Task" else "Edit Task") },
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
                .verticalScroll(rememberScrollState())
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = state.isCompleted,
                    onCheckedChange = viewModel::onCompletionChange
                )
                Text(
                    text = if (state.isCompleted) "Completed" else "Mark as completed",
                    style = MaterialTheme.typography.bodyLarge
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
                    .padding(bottom = 8.dp),
                minLines = 5
            )

            Text("Category", style = MaterialTheme.typography.titleMedium)
            CategoryPicker(
                selectedCategory = state.category,
                onCategorySelected = viewModel::onCategoryChange
            )

            Text("Note Color", style = MaterialTheme.typography.titleMedium)
            ColorPicker(
                selectedColor = state.color,
                onColorSelected = viewModel::onColorChange
            )
        }
    }
}

@Composable
fun CategoryPicker(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("General", "Work", "Personal", "Urgent")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            val isSelected = selectedCategory == category
            AssistChip(
                onClick = { onCategorySelected(category) },
                label = { Text(category) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (isSelected) Color.Black.copy(alpha = 0.1f) else Color.Transparent,
                    labelColor = if (isSelected) Color.Black else Color.Gray
                )
            )
        }
    }
}

@Composable
fun ColorPicker(
    selectedColor: String,
    onColorSelected: (String) -> Unit
) {
    val colors = listOf(
        "#FFF9C4", // Light Yellow
        "#FFECB3", // Light Amber
        "#FFCCBC", // Light Deep Orange
        "#F8BBD0", // Light Pink
        "#E1BEE7", // Light Purple
        "#D1C4E9", // Light Deep Purple
        "#C5CAE9", // Light Indigo
        "#BBDEFB", // Light Blue
        "#B2EBF2", // Light Cyan
        "#B2DFDB", // Light Teal
        "#C8E6C9", // Light Green
        "#DCEDC8"  // Light Lime
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        colors.take(8).forEach { colorHex ->
            val color = Color(android.graphics.Color.parseColor(colorHex))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color, CircleShape)
                    .border(
                        width = if (selectedColor == colorHex) 2.dp else 1.dp,
                        color = if (selectedColor == colorHex) Color.Black else Color.Gray,
                        shape = CircleShape
                    )
                    .clickable { onColorSelected(colorHex) }
            )
        }
    }
}
