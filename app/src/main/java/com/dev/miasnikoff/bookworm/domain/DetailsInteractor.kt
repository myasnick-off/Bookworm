package com.dev.miasnikoff.bookworm.domain

import com.dev.miasnikoff.bookworm.data.remote.model.ApiResponse
import com.dev.miasnikoff.bookworm.domain.model.BookDetails
import com.dev.miasnikoff.bookworm.domain.model.Result
import com.dev.miasnikoff.bookworm.ui.details.mapper.VolumeDetailsMapper
import javax.inject.Inject

class DetailsInteractor @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val mapper: VolumeDetailsMapper,
) {
    suspend fun getDetails(bookId: String): Result<BookDetails> {
        val detailsEntity = localRepository.getBook(bookId)
        var inFavorite = false
        if (detailsEntity != null) {
            if (detailsEntity.inHistory) {
                return Result.Success(data = mapper.fromEntity(detailsEntity))
            }
            inFavorite = detailsEntity.inFavorite
        }
        return when (val response = remoteRepository.getVolume(bookId)) {
            is ApiResponse.Success -> {
                val details = mapper.fromDto(response.data.copy(isFavorite = inFavorite))
                localRepository.saveBook(mapper.toEntity(details))
                Result.Success(data = details)
            }
            is ApiResponse.Failure -> { Result.Error(message = response.message)}
        }
    }
}