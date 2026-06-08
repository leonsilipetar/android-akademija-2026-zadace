package com.test.memoboard.tasks.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.memoboard.tasks.data.remote.SessionManager
import com.test.memoboard.tasks.repository.AuthRepository
import com.test.memoboard.tasks.ui.state.LoginState
import kotlinx.coroutines.launch

class LoginViewModel(
private val repository: AuthRepository
) : ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")

    var state by mutableStateOf<LoginState>(LoginState.Idle)

    fun login(onSuccess: () -> Unit) {

        viewModelScope.launch {

            state = LoginState.Loading

            repository.login(username, password)
                .onSuccess { response ->

                    SessionManager.token = response.token
                    state = LoginState.Success

                    onSuccess()
                }
                .onFailure {
                    state = LoginState.Error("Login failed")
                }
        }
    }
}
