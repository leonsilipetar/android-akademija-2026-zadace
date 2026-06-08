package com.test.memoboard.tasks.model

data class Task(
    val id: String,
    val username: String? = null,
    val title: String,
    val body: String,
    val color: String = "#FFF9C4",
    val isCompleted: Boolean = false,
    val category: String = "General"
)
