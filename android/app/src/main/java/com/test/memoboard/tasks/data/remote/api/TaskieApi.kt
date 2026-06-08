package com.test.memoboard.tasks.data.remote.api

import com.test.memoboard.tasks.data.remote.dto.CreateTaskResponse
import com.test.memoboard.tasks.data.remote.dto.GetAllTasksResponse
import com.test.memoboard.tasks.data.remote.dto.LoginRequest
import com.test.memoboard.tasks.data.remote.dto.LoginResponse
import com.test.memoboard.tasks.data.remote.dto.TaskRequest
import com.test.memoboard.tasks.data.remote.dto.TaskResponse
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
        @Path("id") id: String
    ): TaskResponse

    @POST("tasks/create")
    suspend fun createTask(
        @Body request: TaskRequest
    ): CreateTaskResponse

    @PUT("tasks/{id}")
    suspend fun updateTask(
        @Path("id") id: String,
        @Body request: TaskRequest
    )

    @DELETE("tasks/{id}")
    suspend fun deleteTask(
        @Path("id") id: String
    )
}
