package com.dev.miasnikoff.bookworm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.miasnikoff.bookworm.data.local.model.BookEntity

@Dao
interface BookDao {

    @Query("SELECT * FROM BookEntity")
    fun getAllData(): List<BookEntity>

    @Query("SELECT * FROM BookEntity WHERE in_history = :history")
    fun getAllHistory(history: Boolean = true): List<BookEntity>

    @Query("SELECT * FROM BookEntity WHERE in_favorite = :favorite")
    fun getAllFavorite(favorite: Boolean = true): List<BookEntity>

    @Query("SELECT * FROM BookEntity WHERE id = :bookId")
    fun getBook(bookId: String): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(entity: BookEntity)

    @Query("DELETE FROM BookEntity WHERE id = :bookId")
    fun deleteBook(bookId: String)

    @Query("DELETE FROM BookEntity")
    fun deleteAllData()
}