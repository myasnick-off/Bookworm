package com.dev.miasnikoff.bookworm.di.module

import android.content.Context
import com.dev.miasnikoff.bookworm.data.local.BooksDataBase
import com.dev.miasnikoff.bookworm.data.local.dao.BookDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {

    @Provides
    @Singleton
    fun database(context: Context): BooksDataBase {
        return BooksDataBase.getInstance(context)
    }

    @Provides
    @Singleton
    fun bookDao(dataBase: BooksDataBase): BookDao {
        return dataBase.bookDao()
    }
}