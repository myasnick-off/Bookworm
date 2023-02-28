package com.dev.miasnikoff.network.calladapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ApiResponseCallAdapter(private val responseType: Type) :
    CallAdapter<ApiResponse<*>, Call<ApiResponse<*>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<ApiResponse<*>>): Call<ApiResponse<*>> = ApiResponseCall(call)
}