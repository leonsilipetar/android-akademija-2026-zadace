package com.test.memoboard.tasks.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val username: String?,
    val title: String,
    val body: String,
    val isSynced: Boolean = true,
    val color: String = "#FFF9C4",
    val isCompleted: Boolean = false,
    val category: String = "General"
)
