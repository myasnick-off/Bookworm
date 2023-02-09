package com.dev.miasnikoff.feature_auth.ui

import com.dev.miasnikoff.core.model.UserModel

sealed class AuthState {
    object Loading : AuthState()
    data class Success(val data: UserModel) : AuthState()
    data class AuthFailure(val message: String) : AuthState()
    data class Failure(val message: String) : AuthState()
}