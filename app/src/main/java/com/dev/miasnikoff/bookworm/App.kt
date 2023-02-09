package com.dev.miasnikoff.bookworm

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.dev.miasnikoff.bookworm.di.AppComponent
import com.dev.miasnikoff.bookworm.di.DaggerAppComponent
import com.dev.miasnikoff.core.prefs.SettingsPrefsHelper

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        loadTheme()
    }

    private fun loadTheme() {
        val themePrefs = SettingsPrefsHelper(this)
        AppCompatDelegate.setDefaultNightMode(themePrefs.appTheme.id)
    }
}