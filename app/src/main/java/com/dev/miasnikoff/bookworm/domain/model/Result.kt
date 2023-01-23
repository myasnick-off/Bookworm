package com.dev.miasnikoff.bookworm.domain.model

sealed class Result<out S> {
    data class Success<out S>(val data: S) : Result<S>()
    data class Error(val message: String?) : Result<Nothing>()
}

inline fun <S> Result<S>.onSuccess(action: (success: S) -> Unit): Result<S> =
    this.apply {
        if (this is Result.Success) action(data)
    }

inline fun <S> Result<S>.onFailure(action: (message: String?) -> Unit): Result<S> =
    this.apply {
        if (this is Result.Error) action(message)
    }
