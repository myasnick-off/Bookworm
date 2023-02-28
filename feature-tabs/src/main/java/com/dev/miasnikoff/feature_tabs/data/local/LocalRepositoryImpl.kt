package com.dev.miasnikoff.feature_tabs.data.local

import com.dev.miasnikoff.feature_tabs.domain.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(private val bookDao: BookDao) : LocalRepository {

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
        return withContext(Dispatchers.IO) {
            try {
                bookDao.deleteAllHistory()
                true
            } catch (e: IOException) {
                false
            }
        }
    }

    override suspend fun removeAllFavorite(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                bookDao.deleteAllFavorite()
                true
            } catch (e: IOException) {
                false
            }
        }
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