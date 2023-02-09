package com.dev.miasnikoff.feature_auth.domain

import com.dev.miasnikoff.core.model.UserModel
import io.reactivex.Maybe

interface AuthRepository {
    fun checkAuth(login: String, pass: String): Maybe<UserModel>
}