package com.example.myapplication.tasks.data.mapper

import com.example.myapplication.tasks.data.local.db.TaskEntity
import com.example.myapplication.tasks.data.remote.dto.TaskResponse
import com.example.myapplication.tasks.model.Task

fun TaskResponse.toTask(): Task {

    return Task(
        id = id ?: 0,
        username = username,
        title = title,
        body = body
    )
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        username = username,
        title = title,
        body = body
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        username = username,
        title = title,
        body = body
    )
}