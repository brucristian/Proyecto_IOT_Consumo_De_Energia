package com.example.appvoltaje.adapters.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appvoltaje.domain.model.User
import com.example.appvoltaje.ports.out.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var name by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isAuthSuccess by mutableStateOf(false)

    // Expone getCurrentUser del repositorio para usarse en SplashScreen
    fun getCurrentUser(): User? = authRepository.getCurrentUser()

    fun onLoginClick() {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Por favor, llena todos los campos"
            return
        }
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            authRepository.login(email, password).collect { result ->
                isLoading = false
                result.fold(
                    onSuccess = { isAuthSuccess = true },
                    onFailure = { errorMessage = it.message ?: "Error al iniciar sesión" }
                )
            }
        }
    }

    fun onRegisterClick() {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            errorMessage = "Por favor, llena todos los campos"
            return
        }
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            authRepository.register(name, email, password).collect { result ->
                isLoading = false
                result.fold(
                    onSuccess = { isAuthSuccess = true },
                    onFailure = { errorMessage = it.message ?: "Error al registrarse" }
                )
            }
        }
    }

    fun clearForm() {
        email = ""
        password = ""
        name = ""
        errorMessage = null
        isAuthSuccess = false
    }
}