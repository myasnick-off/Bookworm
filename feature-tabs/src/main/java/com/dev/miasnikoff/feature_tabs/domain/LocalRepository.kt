package com.dev.miasnikoff.feature_tabs.domain

import com.dev.miasnikoff.feature_tabs.data.local.BookEntity

interface LocalRepository {
    suspend fun getAll(): List<BookEntity>
    suspend fun getAllHistory(): List<BookEntity>
    suspend fun getAllFavorite(): List<BookEntity>
    suspend fun removeAllHistory(): Boolean
    suspend fun removeAllFavorite(): Boolean
    suspend fun getBook(bookId: String): BookEntity?
    suspend fun saveBook(book: BookEntity)
    suspend fun removeBook(bookId: String)
}