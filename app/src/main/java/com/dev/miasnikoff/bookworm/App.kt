package com.dev.miasnikoff.bookworm

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.dev.miasnikoff.bookworm.utils.SettingsPrefsHelper

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        loadTheme()
    }

    private fun loadTheme() {
        val themePrefs = SettingsPrefsHelper(this)
        AppCompatDelegate.setDefaultNightMode(themePrefs.appTheme.id)
    }
}