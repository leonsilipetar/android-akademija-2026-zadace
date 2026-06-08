package com.test.memoboard.tasks.data.mapper

import com.test.memoboard.tasks.data.local.db.TaskEntity
import com.test.memoboard.tasks.data.remote.dto.TaskResponse
import com.test.memoboard.tasks.model.Task

fun TaskResponse.toTask(): Task {
    return Task(
        id = id ?: "",
        username = username,
        title = title,
        body = body
    )
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id ,
        username = username,
        title = title,
        body = body,
        color = color,
        isCompleted = isCompleted,
        category = category
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        username = username,
        title = title,
        body = body,
        color = color,
        isCompleted = isCompleted,
        category = category
    )
}
