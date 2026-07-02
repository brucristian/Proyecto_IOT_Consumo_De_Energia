package com.example.appvoltaje.adapters.infrastructure.auth

import com.example.appvoltaje.domain.model.User
import com.example.appvoltaje.ports.out.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl : AuthRepository {

    override fun login(email: String, pass: String): Flow<Result<User>> = flow {
        emit(Result.success(User("1", email, "Usuario de Prueba")))
    }

    override fun register(name: String, email: String, pass: String): Flow<Result<User>> = flow {
        emit(Result.success(User("1", email, name)))
    }

    override fun getCurrentUser(): User? = null

    override fun logout() { }
}