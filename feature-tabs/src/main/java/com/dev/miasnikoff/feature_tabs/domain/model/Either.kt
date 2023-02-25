package com.dev.miasnikoff.feature_tabs.domain.model

sealed class Either<out S> {
    data class Success<out S>(val data: S) : Either<S>()
    data class Error(val message: String? = null) : Either<Nothing>()
}

inline fun <S> Either<S>.onSuccess(action: (success: S) -> Unit): Either<S> =
    this.apply {
        if (this is Either.Success) action(data)
    }

inline fun <S> Either<S>.onFailure(action: (message: String?) -> Unit): Either<S> =
    this.apply {
        if (this is Either.Error) action(message)
    }
