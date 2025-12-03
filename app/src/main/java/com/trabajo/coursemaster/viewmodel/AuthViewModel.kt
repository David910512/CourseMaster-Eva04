package com.trabajo.coursemaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trabajo.coursemaster.data.firebase.AppContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class AuthViewModel : ViewModel() {
    private val repository = AppContainer.authRepository

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)

            val result = repository.login(email, password)

            _authState.value = if (result.isSuccess) {
                AuthState(isSuccess = true)
            } else {
                AuthState(error = result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)

            val result = repository.register(email, password)

            _authState.value = if (result.isSuccess) {
                AuthState(isSuccess = true)
            } else {
                AuthState(error = result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState()
    }
}