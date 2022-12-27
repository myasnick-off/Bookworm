package com.dev.miasnikoff.bookworm.presentation.details.model

sealed class DetailsState {
    object Loading : DetailsState()
    data class Success(val data: Volume) : DetailsState()
    data class Failure(val message: String) : DetailsState()
}