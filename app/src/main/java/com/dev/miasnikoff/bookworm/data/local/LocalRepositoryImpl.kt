package com.dev.miasnikoff.bookworm.data.local

import com.dev.miasnikoff.bookworm.App
import com.dev.miasnikoff.bookworm.data.local.dao.BookDao
import com.dev.miasnikoff.bookworm.data.local.model.BookEntity
import com.dev.miasnikoff.bookworm.domain.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class LocalRepositoryImpl(private val bookDao: BookDao = App.appInstance.database.bookDao()) :
    LocalRepository {

    override suspend fun getAll(): List<BookEntity> {
        return withContext(Dispatchers.IO) {
            try {
                bookDao.getAllData()
            } catch (e: IOException) {
                emptyList()
            }
        }
    }

    override suspend fun getAllHistory(): List<BookEntity> {
        return withContext(Dispatchers.IO) {
            try {
                bookDao.getAllHistory()
            } catch (e: IOException) {
                emptyList()
            }
        }
    }

    override suspend fun getAllFavorite(): List<BookEntity> {
        return withContext(Dispatchers.IO) {
            try {
                bookDao.getAllFavorite()
            } catch (e: IOException) {
                emptyList()
            }
        }
    }

    override suspend fun removeAllHistory(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun removeAllFavorite(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getBook(bookId: String): BookEntity? {
        return withContext(Dispatchers.IO) {
            try {
                bookDao.getBook(bookId).first()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun saveBook(book: BookEntity) {
        withContext(Dispatchers.IO) {
            bookDao.insertBook(book)
        }
    }

    override suspend fun removeBook(bookId: String) {
        withContext(Dispatchers.IO) {
            bookDao.deleteBook(bookId)
        }
    }
}