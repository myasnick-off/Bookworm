package com.dev.miasnikoff.network.calladapter

import kotlinx.serialization.SerializationException
import java.io.IOException

sealed interface ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>
    sealed class Failure(open val message: String?) : ApiResponse<Nothing> {
        data class HttpFailure(val code: Int, override val message: String) : Failure(message)
        data class NetworkFailure(val e: IOException) : Failure(e.message)
        data class ApiFailure(val e: SerializationException) : Failure(e.message)
        data class UnknownFailure(val e: Throwable) : Failure(e.message)
    }
}
