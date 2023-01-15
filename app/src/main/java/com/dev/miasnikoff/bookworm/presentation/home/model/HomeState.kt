package com.dev.miasnikoff.bookworm.presentation.home.model

sealed class HomeState {
    object Loading : HomeState()
    data class Success(val data: HomeData) : HomeState()
    data class Failure(val message: String) : HomeState()
}