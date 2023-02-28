package com.dev.miasnikoff.feature_tabs.ui.details.model

import com.dev.miasnikoff.feature_tabs.domain.model.BookDetails

sealed class DetailsState {
    object Loading : DetailsState()
    data class Success(val data: BookDetails) : DetailsState()
    data class Failure(val message: String) : DetailsState()
}