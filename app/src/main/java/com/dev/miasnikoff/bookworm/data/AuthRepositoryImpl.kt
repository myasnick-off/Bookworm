package com.dev.miasnikoff.bookworm.data

import com.dev.miasnikoff.bookworm.domain.AuthRepository
import com.dev.miasnikoff.bookworm.ui._core.model.UserModel
import io.reactivex.Maybe
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authService: AuthService) :
    AuthRepository {

    override fun checkAuth(login: String, pass: String): Maybe<UserModel> {
        return authService.validateAuth(login, pass)
    }
}