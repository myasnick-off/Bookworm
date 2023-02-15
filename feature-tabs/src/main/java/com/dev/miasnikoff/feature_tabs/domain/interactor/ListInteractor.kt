package com.dev.miasnikoff.feature_tabs.domain.interactor

import com.dev.miasnikoff.feature_tabs.data.local.BookEntity
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeResponse
import com.dev.miasnikoff.feature_tabs.domain.model.Either
import com.dev.miasnikoff.feature_tabs.domain.model.PrintType
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem

interface ListInteractor {

    suspend fun getBooksList(
        query: String,
        filter: String?,
        printType: String? = PrintType.BOOKS.type,
        orderBy: String?,
        startIndex: Int,
        maxResults: Int
    ): Either<VolumeResponse>

    suspend fun saveInFavorite(bookItem: BookItem)
    suspend fun removeFromHistory(bookItem: BookItem)
    suspend fun removeFromFavorite(bookItem: BookItem)
    suspend fun getHistory(): List<BookEntity>
    suspend fun getFavorite(): List<BookEntity>
    suspend fun removeAllHistory()
    suspend fun removeAllFavorite()
}