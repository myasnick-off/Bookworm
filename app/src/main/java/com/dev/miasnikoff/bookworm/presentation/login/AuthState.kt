package com.dev.miasnikoff.bookworm.presentation.login

import com.dev.miasnikoff.bookworm.presentation._core.model.UserModel

sealed class AuthState {
    object Loading : AuthState()
    data class Success(val data: UserModel) : AuthState()
    data class AuthFailure(val message: String) : AuthState()
    data class Failure(val message: String) : AuthState()
}