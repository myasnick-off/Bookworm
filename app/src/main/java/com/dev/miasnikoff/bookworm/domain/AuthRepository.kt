package com.dev.miasnikoff.bookworm.domain

import com.dev.miasnikoff.bookworm.presentation._core.model.UserModel
import io.reactivex.Maybe

interface AuthRepository {
    fun checkAuth(login: String, pass: String): Maybe<UserModel>
}