package com.dev.miasnikoff.feature_tabs.di.module

import android.content.Context
import com.dev.miasnikoff.feature_tabs.data.local.BookDao
import com.dev.miasnikoff.feature_tabs.data.local.BooksDataBase
import dagger.Module
import dagger.Provides

@Module
class TabDBModule {

    @Provides
    fun database(context: Context): BooksDataBase {
        return BooksDataBase.getInstance(context)
    }

    @Provides
    fun bookDao(dataBase: BooksDataBase): BookDao {
        return dataBase.bookDao()
    }
}