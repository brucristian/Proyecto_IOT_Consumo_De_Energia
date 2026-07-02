package com.example.appvoltaje.ports.out
import com.example.appvoltaje.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, pass: String): Flow<Result<User>>
    fun register(name: String, email: String, pass: String): Flow<Result<User>>
    fun getCurrentUser(): User?
    fun logout()
}