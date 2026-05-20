package com.example.myapplication.tasks.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String?,
    val title: String,
    val body: String,
    val isSynced: Boolean = true
)