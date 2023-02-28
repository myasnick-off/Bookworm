package com.dev.miasnikoff.feature_auth.data

import com.dev.miasnikoff.core.model.UserModel
import com.dev.miasnikoff.feature_auth.domain.AuthRepository
import io.reactivex.Maybe
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authService: AuthService) :
    AuthRepository {

    override fun checkAuth(login: String, pass: String): Maybe<UserModel> {
        return authService.validateAuth(login, pass)
    }
}