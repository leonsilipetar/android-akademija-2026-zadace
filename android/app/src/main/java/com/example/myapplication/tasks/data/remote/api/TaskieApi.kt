package com.example.myapplication.tasks.data.remote.api

import com.example.myapplication.tasks.data.remote.dto.CreateTaskResponse
import com.example.myapplication.tasks.data.remote.dto.GetAllTasksResponse
import com.example.myapplication.tasks.data.remote.dto.LoginRequest
import com.example.myapplication.tasks.data.remote.dto.LoginResponse
import com.example.myapplication.tasks.data.remote.dto.TaskRequest
import com.example.myapplication.tasks.data.remote.dto.TaskResponse
import com.example.myapplication.tasks.data.remote.dto.UpdateTaskRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path

interface TaskieApi {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("tasks/all")
    suspend fun getTasks(): GetAllTasksResponse

    @GET("tasks/{id}")
    suspend fun getTaskById(
        @Path("id") id: Int
    ): TaskResponse

    @POST("tasks/create")
    suspend fun createTask(
        @Body request: TaskRequest
    ): CreateTaskResponse

    @PUT("tasks/{id}")
    suspend fun updateTask(
        @Path("id") id: Int,
        @Body request: UpdateTaskRequest
    )

    @DELETE("tasks/{id}")
    suspend fun deleteTask(
        @Path("id") id: Int
    )
}