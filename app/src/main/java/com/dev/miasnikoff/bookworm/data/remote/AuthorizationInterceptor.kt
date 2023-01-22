package com.dev.miasnikoff.bookworm.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(KEY_HEADER, KEY_VALUE)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {
        private const val KEY_HEADER = "key"
        private const val KEY_VALUE = "AIzaSyBaAqPeUdUOHol2ryiTkz4fEktX1LrFeSg"
    }
}