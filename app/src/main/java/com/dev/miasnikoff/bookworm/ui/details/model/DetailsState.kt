package com.dev.miasnikoff.bookworm.ui.details.model

sealed class DetailsState {
    object Loading : DetailsState()
    data class Success(val data: BookDetails) : DetailsState()
    data class Failure(val message: String) : DetailsState()
}