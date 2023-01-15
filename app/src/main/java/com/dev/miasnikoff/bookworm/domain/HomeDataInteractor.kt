package com.dev.miasnikoff.bookworm.domain

import com.dev.miasnikoff.bookworm.data.RepositoryImpl
import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeResponse
import com.dev.miasnikoff.bookworm.domain.model.Filter
import com.dev.miasnikoff.bookworm.domain.model.OrderBy
import com.dev.miasnikoff.bookworm.domain.model.QueryFields
import com.dev.miasnikoff.bookworm.presentation.home.model.Genre
import com.dev.miasnikoff.bookworm.presentation.home.model.GenreData

class HomeDataInteractor(private val repository: Repository = RepositoryImpl()) {

    suspend fun getBookOfDay(): VolumeDTO? {
        for (i: Int in 0..5) {
            val query = "${('А'..'я').random()}${(GenreData.values()).random().query}}"
            val response = repository.getVolumeList(
                query = query,
                maxResults = ENLARGED_MAX_VALUES,
                orderBy = (OrderBy.values()).random().type,
                startIndex = (1..5).random()
            )
            try {
                val volume = response.volumes?.first { volume ->
                    volume.volumeInfo.averageRating?.let { it > 4.0 } ?: false &&
                            volume.volumeInfo.imageLinks != null
                }
                volume?.let { return repository.getVolume(it.id) }
            } catch (e: Throwable) {
            }
        }
        return null
    }

    suspend fun getNewestList(): List<VolumeDTO> {
        for (i: Int in 0..5) {
            val response = repository.getVolumeList(
                query = "+${QueryFields.IN_TITLE.type}",
                orderBy = OrderBy.NEWEST.type,
                startIndex = i
            )
            response.volumes?.let { VolumeDTO ->
                return VolumeDTO.filter { it.volumeInfo.imageLinks != null }.distinctBy { it.id }
            }
        }
        return listOf()
    }

    suspend fun getPopularFreeList(): List<VolumeDTO> {
        for (i: Int in 0..5) {
            val response = repository.getVolumeList(
                query = "+${QueryFields.IN_TITLE.type}",
                filter = Filter.FULL.type,
                orderBy = OrderBy.NEWEST.type
            )
            response.volumes?.let { VolumeDTO ->
                return VolumeDTO.filter { it.volumeInfo.imageLinks != null }.distinctBy { it.id }
            }
        }
        return listOf()
    }

    companion object {
        private const val DEFAULT_MAX_VALUES = 20
        private const val ENLARGED_MAX_VALUES = 40
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
        private const val EMPTY_RESULT_MESSAGE = "Nothing found!"
    }
}