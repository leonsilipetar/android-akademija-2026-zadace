package com.example.myapplication.tasks.data.remote.dto

data class UpdateTaskRequest(
    val id: String,
    val title: String,
    val body: String
)