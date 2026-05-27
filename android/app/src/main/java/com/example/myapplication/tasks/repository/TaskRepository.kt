package com.example.myapplication.tasks.repository

import com.example.myapplication.tasks.data.local.db.TaskDao
import com.example.myapplication.tasks.data.local.db.TaskEntity
import com.example.myapplication.tasks.data.mapper.toEntity
import com.example.myapplication.tasks.data.mapper.toTask
import com.example.myapplication.tasks.data.remote.dto.TaskRequest
import com.example.myapplication.tasks.data.remote.retrofit.RetrofitProvider
import com.example.myapplication.tasks.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TaskRepository(
    private val taskDao: TaskDao
) {
    private val api = RetrofitProvider.api
    private val scope = CoroutineScope(Dispatchers.IO)

    val tasks: StateFlow<List<Task>> = taskDao.getAllTasks()
        .map { entities -> entities.map { it.toTask() } }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    suspend fun refreshTasks(): Result<Unit> {
        return try {
            val response = api.getTasks()
            val remoteTasks = response.tasks
            
            // Fetch local tasks to preserve local-only data
            val localTasks = taskDao.getAllTasksSync()
            val localDataMap = localTasks.associateBy { it.id }

            val entities = remoteTasks.map { remoteTask ->
                val serverId = remoteTask.id ?: ""
                val localTask = localDataMap[serverId]
                
                TaskEntity(
                    id = serverId,
                    username = remoteTask.username,
                    title = remoteTask.title,
                    body = remoteTask.body,
                    isSynced = true,
                    color = localTask?.color ?: "#FFF9C4",
                    isCompleted = localTask?.isCompleted ?: false,
                    category = localTask?.category ?: "General"
                )
            }

            taskDao.clearAll()
            taskDao.insertTasks(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTaskById(id: String): Result<Task> {
        val localTask = taskDao.getTaskById(id)?.toTask()
        if (localTask != null) return Result.success(localTask)
        
        return try {
            val response = api.getTaskById(id)
            val task = response.toTask().copy(id = id)
            taskDao.insertTask(task.toEntity())
            Result.success(task)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createTask(request: TaskRequest, color: String, category: String): Result<String> {
        return try {
            val response = api.createTask(request)
            val serverId = response.id

            val syncedTask = TaskEntity(
                id = serverId,
                username = null,
                title = request.title,
                body = request.body,
                isSynced = true,
                color = color,
                category = category
            )
            taskDao.insertTask(syncedTask)
            Result.success(serverId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateTask(
        id: String, 
        title: String, 
        body: String, 
        color: String, 
        isCompleted: Boolean, 
        category: String
    ): Result<Unit> {
        return try {
            val request = TaskRequest(
                title = title,
                body = body
            )
            api.updateTask(id, request)
            
            val task = Task(
                id = id, 
                username = null, 
                title = title, 
                body = body, 
                color = color, 
                isCompleted = isCompleted, 
                category = category
            )
            taskDao.insertTask(task.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteTask(id: String): Result<Unit> {
        return try {
            api.deleteTask(id)
            taskDao.deleteTask(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
