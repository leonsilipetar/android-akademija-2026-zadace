package com.example.myapplication.tasks.repository

import com.example.myapplication.tasks.data.local.datastore.TokenManager
import com.example.myapplication.tasks.data.remote.SessionManager
import com.example.myapplication.tasks.data.remote.api.TaskieApi
import com.example.myapplication.tasks.data.remote.dto.LoginRequest
import com.example.myapplication.tasks.data.remote.dto.LoginResponse

class AuthRepository(
    private val api: TaskieApi,
    private val tokenManager: TokenManager
) {

    val tokenFlow = tokenManager.tokenFlow
    val usernameFlow = tokenManager.usernameFlow

    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(username, password))

            tokenManager.saveAuthData(response.token, username)
            SessionManager.token = response.token

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        tokenManager.clearToken()
        SessionManager.token = null
    }
}
