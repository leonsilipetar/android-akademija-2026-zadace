package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.tasks.data.local.datastore.TokenManager
import com.example.myapplication.tasks.data.local.db.TaskDatabase
import com.example.myapplication.tasks.data.remote.retrofit.RetrofitProvider
import com.example.myapplication.tasks.navigation.AppNavigation
import com.example.myapplication.tasks.repository.AuthRepository
import com.example.myapplication.tasks.repository.TaskRepository

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenManager = TokenManager(applicationContext)
        val database = TaskDatabase.getDatabase(applicationContext)
        val taskDao = database.taskDao()

        val api = RetrofitProvider.api

        val authRepository = AuthRepository(api, tokenManager)
        val taskRepository = TaskRepository(taskDao)

        setContent {
            AppNavigation(
                authRepository = authRepository,
                taskRepository = taskRepository
            )
        }
    }
}