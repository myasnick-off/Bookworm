package com.dev.miasnikoff.feature_tabs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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

    @Query("DELETE FROM BookEntity WHERE in_history = :history")
    fun deleteAllHistory(history: Boolean = true)

    @Query("DELETE FROM BookEntity WHERE in_favorite = :favorite")
    fun deleteAllFavorite(favorite: Boolean = true)
}
