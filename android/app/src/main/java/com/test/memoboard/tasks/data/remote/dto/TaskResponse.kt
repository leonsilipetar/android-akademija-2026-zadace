package com.test.memoboard.tasks.data.remote.dto

data class TaskResponse(
    val id: String? = null,
    val username: String? = null,
    val title: String,
    val body: String
)
