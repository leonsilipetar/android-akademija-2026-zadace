package com.test.memoboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.test.memoboard.tasks.data.local.datastore.TokenManager
import com.test.memoboard.tasks.data.local.db.TaskDatabase
import com.test.memoboard.tasks.data.remote.retrofit.RetrofitProvider
import com.test.memoboard.tasks.navigation.AppNavigation
import com.test.memoboard.tasks.repository.AuthRepository
import com.test.memoboard.tasks.repository.TaskRepository

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
