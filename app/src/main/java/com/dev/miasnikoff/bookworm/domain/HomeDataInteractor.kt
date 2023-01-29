package com.dev.miasnikoff.bookworm.domain

import com.dev.miasnikoff.bookworm.data.RemoteRepositoryImpl
import com.dev.miasnikoff.bookworm.data.local.LocalRepositoryImpl
import com.dev.miasnikoff.bookworm.data.local.model.BookEntity
import com.dev.miasnikoff.bookworm.data.remote.model.ApiResponse
import com.dev.miasnikoff.bookworm.data.remote.model.VolumeDTO
import com.dev.miasnikoff.bookworm.domain.model.Filter
import com.dev.miasnikoff.bookworm.domain.model.OrderBy
import com.dev.miasnikoff.bookworm.domain.model.QueryFields
import com.dev.miasnikoff.bookworm.domain.model.Result
import javax.inject.Inject

class HomeDataInteractor @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) {

    suspend fun getBookOfDay(): Result<VolumeDTO?> {
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
                        response.data.volumes?.firstOrNull { volume -> volume.volumeInfo.imageLinks != null }
                    volume?.let {
                        return when (val detailsResponse = remoteRepository.getVolume(it.id)) {
                            is ApiResponse.Success -> Result.Success(data = detailsResponse.data)
                            is ApiResponse.Failure -> Result.Error(detailsResponse.message)
                        }
                    }
                }
                is ApiResponse.Failure -> return Result.Error(response.message)
            }
        }
        return Result.Success(data = null)
    }

    suspend fun getNewestList(): Result<List<VolumeDTO>> {
        for (i: Int in 0..5) {
            val response = remoteRepository.getVolumeList(
                query = "+${QueryFields.IN_TITLE.type}",
                orderBy = OrderBy.NEWEST.type,
                startIndex = i
            )
            when (response) {
                is ApiResponse.Success -> {
                    response.data.volumes?.let { volumeList ->
                        return Result.Success(data = handleResult(volumeList))
                    }
                }
                is ApiResponse.Failure -> return Result.Error(response.message)
            }
        }
        return Result.Success(data = listOf())
    }

    suspend fun getPopularFreeList(): Result<List<VolumeDTO>> {
        for (i: Int in 0..5) {
            val response = remoteRepository.getVolumeList(
                query = "+${QueryFields.IN_TITLE.type}",
                filter = Filter.FREE_BOOKS.type,
                orderBy = OrderBy.NEWEST.type
            )
            when (response) {
                is ApiResponse.Success -> {
                    response.data.volumes?.let { volumeList ->
                        return Result.Success(data = handleResult(volumeList))
                    }
                }
                is ApiResponse.Failure -> return Result.Error(response.message)
            }
        }
        return Result.Success(data = listOf())
    }

    suspend fun getHistory(): List<BookEntity> {
        return localRepository.getAllHistory()
    }

    private fun handleResult(volumeList: List<VolumeDTO>): List<VolumeDTO> {
        return volumeList.filter { it.volumeInfo.imageLinks != null }.distinctBy { it.id }
    }

    companion object {
        private const val ENLARGED_MAX_VALUES = 40
    }
}