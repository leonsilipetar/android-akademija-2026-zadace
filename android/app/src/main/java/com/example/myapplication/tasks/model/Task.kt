package com.example.myapplication.tasks.model

data class Task(
    val id: Int,
    val username: String? = null,
    val title: String,
    val body: String
)