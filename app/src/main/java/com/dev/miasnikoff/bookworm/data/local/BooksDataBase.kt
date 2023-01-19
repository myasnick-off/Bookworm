package com.dev.miasnikoff.bookworm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.miasnikoff.bookworm.data.local.dao.BookDao
import com.dev.miasnikoff.bookworm.data.local.model.BookEntity

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class BooksDataBase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        private const val DB_NAME = "books_database.db"
        private var instance: BooksDataBase? = null

        fun getInstance(context: Context): BooksDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, BooksDataBase::class.java, DB_NAME).build()
            }
            return instance!!
        }
    }
}