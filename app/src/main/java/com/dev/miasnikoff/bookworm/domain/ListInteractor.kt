package com.dev.miasnikoff.bookworm.domain

import com.dev.miasnikoff.bookworm.data.RepositoryImpl
import com.dev.miasnikoff.bookworm.data.local.LocalRepositoryImpl
import com.dev.miasnikoff.bookworm.data.local.model.BookEntity
import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeResponse
import com.dev.miasnikoff.bookworm.domain.mapper.BookEntityDataMapper
import com.dev.miasnikoff.bookworm.domain.model.PrintType
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookItem

class ListInteractor(
    private val remoteRepository: Repository = RepositoryImpl(),
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
    ): VolumeResponse {
        var response = remoteRepository.getVolumeList(
            query = query,
            filter = filter,
            printType = printType,
            orderBy = orderBy,
            startIndex = startIndex,
            maxResults = maxResults
        )
        val favoriteList = localRepository.getAllFavorite()
        val resultList = mutableListOf<VolumeDTO>()
        response.volumes?.let { books ->
            resultList.addAll(books)
            favoriteList.forEach { favorite ->
                val index = books.indexOfFirst { it.id == favorite.id }
                if (index > -1) {
                    resultList[index] = resultList[index].copy(isFavorite = true)
                }
            }
            response = response.copy(volumes = resultList)
        }
        return response
    }

    suspend fun saveInFavorite(bookItem: BookItem) {
        val historyList = localRepository.getAllHistory()
        val index = historyList.indexOfFirst { it.id == bookItem.id }
        val favoriteEntity = if (index > -1) historyList[index].copy(inFavorite = true)
        else mapper.toFavorite(bookItem)
        localRepository.saveBook(favoriteEntity)
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