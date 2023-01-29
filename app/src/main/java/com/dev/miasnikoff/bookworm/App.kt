package com.dev.miasnikoff.bookworm

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.dev.miasnikoff.bookworm.data.local.BooksDataBase
import com.dev.miasnikoff.bookworm.di.AppComponent
import com.dev.miasnikoff.bookworm.di.DaggerAppComponent
import com.dev.miasnikoff.bookworm.di.module.ContextModule
import com.dev.miasnikoff.bookworm.utils.SettingsPrefsHelper

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        loadTheme()
    }

    private fun loadTheme() {
        val themePrefs = SettingsPrefsHelper(this)
        AppCompatDelegate.setDefaultNightMode(themePrefs.appTheme.id)
    }

    companion object {
        lateinit var appInstance: App
    }
}