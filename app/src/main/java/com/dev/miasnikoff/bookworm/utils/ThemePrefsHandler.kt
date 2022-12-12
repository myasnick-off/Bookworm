package com.dev.miasnikoff.bookworm.utils

import android.content.Context

class ThemePrefsHandler(context: Context) {

    private val prefs = context.getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE)

    val appTheme: Themes
        get() {
            val name = prefs.getString(THEME_KEY, Themes.AS_SYSTEM.name)
            return name?.let { Themes.valueOf(it) } ?: Themes.AS_SYSTEM
        }

    fun save(appTheme: Themes) {
        prefs.edit().putString(THEME_KEY, appTheme.name).apply()
    }

    companion object {
        private const val SETTINGS_PREFS = "settings_prefs"
        private const val THEME_KEY = "theme_key"
    }
}