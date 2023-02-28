package com.dev.miasnikoff.feature_auth.data

import com.dev.miasnikoff.core.model.UserModel
import io.reactivex.Maybe

interface AuthService {
    fun validateAuth(login: String, pass: String): Maybe<UserModel>
}