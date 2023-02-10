package com.dev.miasnikoff.feature_auth.ui

sealed class AuthState {
    object Loading : AuthState()
    data class AuthFailure(val message: String) : AuthState()
    data class Failure(val message: String) : AuthState()
}