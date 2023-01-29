package com.dev.miasnikoff.bookworm.domain

import com.dev.miasnikoff.bookworm.data.RemoteRepositoryImpl
import com.dev.miasnikoff.bookworm.data.local.LocalRepositoryImpl
import com.dev.miasnikoff.bookworm.data.local.model.BookEntity
import com.dev.miasnikoff.bookworm.data.remote.model.ApiResponse
import com.dev.miasnikoff.bookworm.data.remote.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.remote.model.VolumeResponse
import com.dev.miasnikoff.bookworm.domain.mapper.BookEntityDataMapper
import com.dev.miasnikoff.bookworm.domain.model.PrintType
import com.dev.miasnikoff.bookworm.domain.model.Result
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookItem

class ListInteractor(
    private val remoteRepository: RemoteRepository = RemoteRepositoryImpl(),
    private val localRepository: LocalRepository = LocalRepositoryImpl(),
    private val mapper: BookEntityDataMapper = BookEntityDataMapper(),
) {

    suspend fun getBooksList(
        query: String,
        filter: String?,
        printType: String? = PrintType.BOOKS.type,
        orderBy: String?,
        startIndex: Int,
        maxResults: Int
    ): Result<VolumeResponse> {
        val response = remoteRepository.getVolumeList(
            query = query,
            filter = filter,
            printType = printType,
            orderBy = orderBy,
            startIndex = startIndex,
            maxResults = maxResults
        )
        val favoriteList = localRepository.getAllFavorite()
        val resultList = mutableListOf<VolumeDTO>()
        return when (response) {
            is ApiResponse.Success -> {
                response.data.volumes?.let { books ->
                    resultList.addAll(books)
                    favoriteList.forEach { favorite ->
                        val index = books.indexOfFirst { it.id == favorite.id }
                        if (index > -1) {
                            resultList[index] = resultList[index].copy(isFavorite = true)
                        }
                    }
                }
                Result.Success(data = response.data.copy(volumes = resultList))
            }
            is ApiResponse.Failure -> { Result.Error(response.message) }
        }
    }

    suspend fun saveInFavorite(bookItem: BookItem) {
        val historyList = localRepository.getAllHistory()
        val index = historyList.indexOfFirst { it.id == bookItem.id }
        val favoriteEntity = if (index > -1) historyList[index].copy(inFavorite = true)
        else mapper.toFavorite(bookItem)
        localRepository.saveBook(favoriteEntity)
    }

    suspend fun removeFromHistory(bookItem: BookItem) {
        if (bookItem.isFavorite) {
            localRepository.saveBook(mapper.toFavorite(bookItem).copy(inHistory = false))
        } else {
            localRepository.removeBook(bookItem.id)
        }
    }

    suspend fun removeFromFavorite(bookItem: BookItem) {
        val historyList = localRepository.getAllHistory()
        val index = historyList.indexOfFirst { it.id == bookItem.id }
        if (index > -1) {
            localRepository.saveBook(historyList[index].copy(inFavorite = false))
        } else {
            localRepository.removeBook(bookItem.id)
        }
    }

    suspend fun getHistory(): List<BookEntity> {
        return localRepository.getAllHistory()
    }

    suspend fun getFavorite(): List<BookEntity> {
        return localRepository.getAllFavorite()
    }

    suspend fun removeAllHistory() {
        localRepository.removeAllHistory()
    }

    suspend fun removeAllFavorite() {
        localRepository.removeAllFavorite()
    }
}