package com.dev.miasnikoff.bookworm.data.remote

import com.dev.miasnikoff.bookworm.data.remote.model.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }
        check(returnType is ParameterizedType)
        val innerType = getParameterUpperBound(0, returnType)
        if (getRawType(innerType) != ApiResponse::class.java) {
            return null
        }
        check(innerType is ParameterizedType)
        val responseType = getParameterUpperBound(0, innerType)
        return ApiResponseCallAdapter(responseType)
    }
}