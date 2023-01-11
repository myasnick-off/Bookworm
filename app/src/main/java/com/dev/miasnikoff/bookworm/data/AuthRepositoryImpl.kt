package com.dev.miasnikoff.bookworm.data

import com.dev.miasnikoff.bookworm.domain.AuthRepository
import com.dev.miasnikoff.bookworm.presentation._core.model.UserModel
import io.reactivex.Maybe

class AuthRepositoryImpl(private val authService: AuthService = AuthService()) : AuthRepository {

    override fun checkAuth(login: String, pass: String): Maybe<UserModel> {
        return authService.validateAuth(login, pass)
    }
}