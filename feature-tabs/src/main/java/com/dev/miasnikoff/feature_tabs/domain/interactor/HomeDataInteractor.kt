package com.dev.miasnikoff.feature_tabs.domain.interactor

import com.dev.miasnikoff.feature_tabs.data.local.BookEntity
import com.dev.miasnikoff.feature_tabs.data.remote.model.BookDTO
import com.dev.miasnikoff.feature_tabs.domain.LocalRepository
import com.dev.miasnikoff.feature_tabs.domain.RemoteRepository
import com.dev.miasnikoff.feature_tabs.domain.model.Either
import com.dev.miasnikoff.feature_tabs.domain.model.Filter
import com.dev.miasnikoff.feature_tabs.domain.model.OrderBy
import com.dev.miasnikoff.feature_tabs.domain.model.QueryFields
import com.dev.miasnikoff.network.calladapter.ApiResponse
import javax.inject.Inject

class HomeDataInteractor @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) {

    suspend fun getBookOfDay(): Either<BookDTO?> {
        for (i: Int in 0..5) {
            val query = "${('А'..'я').random()}+${QueryFields.IN_TITLE.type}${('А'..'я').random()}}"
            val response = remoteRepository.getVolumeList(
                query = query,
                maxResults = ENLARGED_MAX_VALUES,
                orderBy = (OrderBy.values()).random().type,
                startIndex = (1..5).random()
            )
            when (response) {
                is ApiResponse.Success -> {
                    val volume =
                        response.data.volumes?.firstOrNull { volume -> volume.bookInfo.imageLinks != null }
                    volume?.let {
                        return when (val detailsResponse = remoteRepository.getVolume(it.id)) {
                            is ApiResponse.Success -> Either.Success(data = detailsResponse.data)
                            is ApiResponse.Failure -> Either.Error(detailsResponse.message)
                        }
                    }
                }
                is ApiResponse.Failure -> return Either.Error(response.message)
            }
        }
        return Either.Success(data = null)
    }

    suspend fun getNewestList(): Either<List<BookDTO>> {
        for (i: Int in 0..5) {
            val response = remoteRepository.getVolumeList(
                query = "+${QueryFields.IN_TITLE.type}",
                orderBy = OrderBy.NEWEST.type,
                startIndex = i
            )
            when (response) {
                is ApiResponse.Success -> {
                    response.data.volumes?.let { volumeList ->
                        return Either.Success(data = handleResult(volumeList))
                    }
                }
                is ApiResponse.Failure -> return Either.Error(response.message)
            }
        }
        return Either.Success(data = listOf())
    }

    suspend fun getPopularFreeList(): Either<List<BookDTO>> {
        for (i: Int in 0..5) {
            val response = remoteRepository.getVolumeList(
                query = "+${QueryFields.IN_TITLE.type}",
                filter = Filter.FREE_BOOKS.type,
                orderBy = OrderBy.NEWEST.type
            )
            when (response) {
                is ApiResponse.Success -> {
                    response.data.volumes?.let { volumeList ->
                        return Either.Success(data = handleResult(volumeList))
                    }
                }
                is ApiResponse.Failure -> return Either.Error(response.message)
            }
        }
        return Either.Success(data = listOf())
    }

    suspend fun getHistory(): List<BookEntity> {
        return localRepository.getAllHistory()
    }

    private fun handleResult(volumeList: List<BookDTO>): List<BookDTO> {
        return volumeList.filter { it.bookInfo.imageLinks != null }.distinctBy { it.id }
    }

    companion object {
        private const val ENLARGED_MAX_VALUES = 40
    }
}