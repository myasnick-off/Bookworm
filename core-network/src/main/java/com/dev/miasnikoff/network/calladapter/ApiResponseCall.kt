package com.dev.miasnikoff.network.calladapter

import kotlinx.serialization.SerializationException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ApiResponseCall(private val call: Call<ApiResponse<*>>) : Call<ApiResponse<*>> by call {

    override fun enqueue(callback: Callback<ApiResponse<*>>) =
        call.enqueue(object : Callback<ApiResponse<*>> {
            override fun onResponse(
                call: Call<ApiResponse<*>>,
                response: Response<ApiResponse<*>>
            ) {
                if (response.isSuccessful) {
                    callback.onResponse(
                        call,
                        Response.success(ApiResponse.Success(checkNotNull(response.body())))
                    )
                } else {
                    callback.onResponse(
                        call,
                        Response.success(
                            ApiResponse.Failure.HttpFailure(
                                code = response.code(),
                                message = response.message()
                            )
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ApiResponse<*>>, exception: Throwable) {
                when (exception) {
                    is IOException -> {
                        callback.onResponse(
                            call, Response.success(ApiResponse.Failure.NetworkFailure(e = exception))
                        )
                    }
                    is SerializationException -> {
                        callback.onResponse(
                            call,
                            Response.success(ApiResponse.Failure.ApiFailure(e = exception))
                        )
                    }
                    else -> {
                        callback.onResponse(
                            call,
                            Response.success(ApiResponse.Failure.UnknownFailure(e = exception))
                        )
                    }
                }
            }
        })
}