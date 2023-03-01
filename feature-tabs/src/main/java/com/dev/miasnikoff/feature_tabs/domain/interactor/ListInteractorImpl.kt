package com.dev.miasnikoff.feature_tabs.domain.interactor

import com.dev.miasnikoff.feature_tabs.data.local.BookEntity
import com.dev.miasnikoff.feature_tabs.data.remote.model.BookDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeResponse
import com.dev.miasnikoff.feature_tabs.domain.LocalRepository
import com.dev.miasnikoff.feature_tabs.domain.RemoteRepository
import com.dev.miasnikoff.feature_tabs.domain.mapper.BookEntityDataMapper
import com.dev.miasnikoff.feature_tabs.domain.model.Either
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem
import com.dev.miasnikoff.network.calladapter.ApiResponse
import javax.inject.Inject

class ListInteractorImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val mapper: BookEntityDataMapper,
): ListInteractor {

    override suspend fun getBooksList(
        query: String,
        filter: String?,
        printType: String?,
        orderBy: String?,
        startIndex: Int,
        maxResults: Int
    ): Either<VolumeResponse> {
        val response = remoteRepository.getVolumeList(
            query = query,
            filter = filter,
            printType = printType,
            orderBy = orderBy,
            startIndex = startIndex,
            maxResults = maxResults
        )
        val favoriteList = localRepository.getAllFavorite()
        val resultList = mutableListOf<BookDTO>()
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
                Either.Success(data = response.data.copy(volumes = resultList))
            }
            is ApiResponse.Failure -> { Either.Error(response.message) }
        }
    }

    override suspend fun saveInFavorite(bookItem: BookItem) {
        val historyList = localRepository.getAllHistory()
        val index = historyList.indexOfFirst { it.id == bookItem.id }
        val favoriteEntity = if (index > -1) historyList[index].copy(inFavorite = true)
        else mapper.toFavorite(bookItem)
        localRepository.saveBook(favoriteEntity)
    }

    override suspend fun removeFromHistory(bookItem: BookItem) {
        if (bookItem.isFavorite) {
            localRepository.saveBook(mapper.toFavorite(bookItem).copy(inHistory = false))
        } else {
            localRepository.removeBook(bookItem.id)
        }
    }

    override suspend fun removeFromFavorite(bookItem: BookItem) {
        val historyList = localRepository.getAllHistory()
        val index = historyList.indexOfFirst { it.id == bookItem.id }
        if (index > -1) {
            localRepository.saveBook(historyList[index].copy(inFavorite = false))
        } else {
            localRepository.removeBook(bookItem.id)
        }
    }

    override suspend fun getHistory(): List<BookEntity> {
        return localRepository.getAllHistory()
    }

    override suspend fun getFavorite(): List<BookEntity> {
        return localRepository.getAllFavorite()
    }

    override suspend fun removeAllHistory() {
        localRepository.removeAllHistory()
    }

    override suspend fun removeAllFavorite() {
        localRepository.removeAllFavorite()
    }
}