package com.dev.miasnikoff.feature_tabs.domain.interactor

import com.dev.miasnikoff.feature_tabs.domain.LocalRepository
import com.dev.miasnikoff.feature_tabs.domain.RemoteRepository
import com.dev.miasnikoff.feature_tabs.domain.model.BookDetails
import com.dev.miasnikoff.feature_tabs.domain.model.Either
import com.dev.miasnikoff.feature_tabs.ui.details.mapper.BookDetailsMapper
import com.dev.miasnikoff.network.calladapter.ApiResponse
import javax.inject.Inject

class DetailsInteractor @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val mapper: BookDetailsMapper,
) {
    suspend fun getDetails(bookId: String): Either<BookDetails> {
        val detailsEntity = localRepository.getBook(bookId)
        var inFavorite = false
        if (detailsEntity != null) {
            if (detailsEntity.inHistory) {
                return Either.Success(data = mapper.fromEntity(detailsEntity))
            }
            inFavorite = detailsEntity.inFavorite
        }
        return when (val response = remoteRepository.getVolume(bookId)) {
            is ApiResponse.Success -> {
                val details = mapper.fromDto(response.data.copy(isFavorite = inFavorite))
                localRepository.saveBook(mapper.toEntity(details))
                Either.Success(data = details)
            }
            is ApiResponse.Failure -> { Either.Error(message = response.message)}
        }
    }
}