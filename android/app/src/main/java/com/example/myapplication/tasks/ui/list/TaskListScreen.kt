package com.example.myapplication.tasks.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.tasks.model.Task
import com.example.myapplication.tasks.ui.TaskCard
import com.example.myapplication.tasks.ui.state.BackgroundPreset
import com.example.myapplication.tasks.ui.components.CustomButton
import com.example.myapplication.tasks.ui.components.TitleText

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun TaskListScreen(
    tasks: List<Task>,
    username: String?,
    onAddClick: () -> Unit,
    onNoteClick: (String) -> Unit,
    onDeleteConfirm: (String) -> Unit,
    onBackgroundChange: (String) -> Unit,
    isDark: Boolean,
    onRandomPick: (String) -> Unit,
    onLogout: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var taskToDelete by remember { mutableStateOf<Task?>(null) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }

    val categories = listOf("All") + tasks.map { it.category }.distinct().sorted()

    val filteredItems = tasks.filter {
        (selectedCategory == "All" || it.category == selectedCategory) &&
        (it.title.contains(query, ignoreCase = true) || it.body.contains(query, ignoreCase = true))
    }

    val textColor = if (isDark) Color.White else Color.DarkGray
    val subtitleColor = if (isDark) Color.LightGray else Color.Gray

    Scaffold(
        containerColor = Color.Transparent,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TitleText("MemoBoard", textColor)
                Box {
                    Text(
                        text = username ?: "Guest",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        modifier = Modifier
                            .clickable { showMenu = true }
                            .padding(8.dp)
                    )

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = {
                                showMenu = false
                                showSettingsDialog = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                showMenu = false
                                showLogoutDialog = true
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search tasks...", color = textColor.copy(alpha = 0.5f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = textColor.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,

                    focusedContainerColor = if (isDark) Color.White.copy(alpha = 0.15f) else Color.Black.copy(alpha = 0.04f),
                    unfocusedContainerColor = if (isDark) Color.White.copy(alpha = 0.1f) else Color.Black.copy(alpha = 0.02f),

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )


            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { category ->
                    val isSelected = selectedCategory == category

                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        label = {
                            Text(
                                text = category,
                                color = if (isSelected) {
                                    if (isDark) Color.Black else Color.White
                                } else {
                                    textColor.copy(alpha = 0.7f)
                                },
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = if (isDark) Color.White else Color(0xFF1C1B1F),
                            containerColor = if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.03f)
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = textColor.copy(alpha = 0.2f),
                            selectedBorderColor = Color.Transparent,
                            borderWidth = 1.dp,
                            selectedBorderWidth = 0.dp
                        )
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            if (filteredItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tasks found", color = textColor)
                }
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalItemSpacing = 8.dp
                ) {
                    items(filteredItems) { task ->
                        TaskCard(
                            data = task,
                            onClick = { onNoteClick(task.id) },
                            onLongClick = { taskToDelete = task }
                        )
                    }
                }
            }
        }
    }

    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text("Choose Background") },
            text = {
                Column {
                    Text("Select a color preset or board style:", modifier = Modifier.padding(bottom = 12.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        items(BackgroundPreset.allPresets) { preset ->
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.LightGray, CircleShape)
                                    .clickable {
                                        onBackgroundChange(preset.id)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (preset.imageResId != null) {
                                    Image(
                                        painter = painterResource(id = preset.imageResId),
                                        contentDescription = preset.name,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    if (preset.secondaryColor != null) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(
                                                    Brush.verticalGradient(
                                                        colors = listOf(preset.primaryColor, preset.secondaryColor)
                                                    )
                                                )
                                        )
                                        } else {
                                            Box(
                                                modifier = Modifier.fillMaxSize().background(preset.primaryColor)
                                            )
                                        }
                                }
                            }
                        }
                    }
                }
                   },confirmButton = {
                       TextButton(onClick = {
                           showSettingsDialog = false
                       }) {Text("Done")}
                   })
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

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    onLogout()
                }) {
                    Text("Logout", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
