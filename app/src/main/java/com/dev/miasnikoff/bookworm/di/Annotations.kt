package com.dev.miasnikoff.bookworm.di

import javax.inject.Qualifier

@Qualifier
annotation class BaseUrl

@Qualifier
annotation class ApiKeyIntercept

@Qualifier
annotation class LoggingIntercept

@Qualifier
annotation class JsonConverter

@Qualifier
annotation class ApiResponseAdapter

@Qualifier
annotation class CoroutineAdapter
