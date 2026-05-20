package com.example.myapplication.tasks.ui.list

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.tasks.model.Task
import com.example.myapplication.zadaca.components.CustomButton
import com.example.myapplication.zadaca.components.TitleText

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun TaskListScreen(
    tasks: List<Task>,
    onAddClick: () -> Unit,
    onNoteClick: (Int) -> Unit,
    onDeleteConfirm: (Int) -> Unit,
    onRandomPick: (Int) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var taskToDelete by remember { mutableStateOf<Task?>(null) }

    val filteredItems = tasks.filter {
        it.title.contains(query, ignoreCase = true) ||
                it.body.contains(query, ignoreCase = true)
    }

    Scaffold(
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                FloatingActionButton(
                    onClick = {
                        if (tasks.isNotEmpty()) {
                            onRandomPick(tasks.random().id)
                        }
                    },
                    containerColor = Color(0xFFFFEB3B), // Yellow
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(Icons.Default.Casino, contentDescription = "Pick random task")
                }
                CustomButton("+ Add Task", onClick = onAddClick)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TitleText("My Sticky Tasks")
            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search tasks...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tasks found", color = Color.Gray)
                }
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalItemSpacing = 8.dp
                ) {
                    items(filteredItems) { task ->
                        StickyNoteCard(
                            task = task,
                            onClick = { onNoteClick(task.id) },
                            onLongClick = { taskToDelete = task }
                        )
                    }
                }
            }
        }
    }

    if (taskToDelete != null) {
        AlertDialog(
            onDismissRequest = { taskToDelete = null },
            title = { Text("Delete Note?") },
            text = { Text("Are you sure you want to delete '${taskToDelete?.title}'?") },
            confirmButton = {
                TextButton(onClick = {
                    taskToDelete?.let { onDeleteConfirm(it.id) }
                    taskToDelete = null
                }) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { taskToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun StickyNoteCard(
    task: Task,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val colors = listOf(
        Color(0xFFFFF9C4), // Light Yellow
        Color(0xFFFFECB3), // Light Amber
        Color(0xFFFFCCBC), // Light Deep Orange
        Color(0xFFF8BBD0), // Light Pink
        Color(0xFFE1BEE7), // Light Purple
        Color(0xFFD1C4E9), // Light Deep Purple
        Color(0xFFC5CAE9), // Light Indigo
        Color(0xFFBBDEFB), // Light Blue
        Color(0xFFB2EBF2), // Light Cyan
        Color(0xFFB2DFDB), // Light Teal
        Color(0xFFC8E6C9), // Light Green
        Color(0xFFDCEDC8)  // Light Lime
    )
     val colorIndex = remember(task.id) { java.util.Random(task.id.toLong()).nextInt(colors.size) }
    val bgColor = colors[colorIndex]

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = task.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = task.body,
                fontSize = 14.sp,
                maxLines = 6
            )
        }
    }
}