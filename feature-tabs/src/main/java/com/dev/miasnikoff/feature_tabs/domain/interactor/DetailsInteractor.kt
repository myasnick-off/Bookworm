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
        localRepository.getBook(bookId)?.let { detailsEntity ->
            if (detailsEntity.inHistory.not()) {
                localRepository.saveBook(detailsEntity.copy(inHistory = true))
            }
            return Either.Success(data = mapper.fromEntity(detailsEntity))
        }
        return when (val response = remoteRepository.getVolume(bookId)) {
            is ApiResponse.Success -> {
                val details = mapper.fromDto(response.data)
                localRepository.saveBook(mapper.toEntity(details))
                Either.Success(data = details)
            }
            is ApiResponse.Failure -> { Either.Error(message = response.message)}
        }
    }

    suspend fun checkFavorite(bookId: String): Either<Boolean> {
        localRepository.getBook(bookId)?.let { detailsEntity ->
            val newDetailsEntity = detailsEntity.copy(inFavorite = !detailsEntity.inFavorite)
            localRepository.saveBook(newDetailsEntity)
            return Either.Success(data = true)
        }
        return Either.Error()
    }
}