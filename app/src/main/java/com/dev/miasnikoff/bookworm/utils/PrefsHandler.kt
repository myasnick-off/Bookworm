package com.dev.miasnikoff.bookworm.utils

import android.content.Context

class PrefsHandler(context: Context) {

    private val prefs = context.getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE)

    fun save(key: String, number: Int) {
        prefs.edit().putInt(key, number).apply()
    }

    fun loadInt(key: String): Int = prefs.getInt(key, -1)

    companion object {
        private const val SETTINGS_PREFS = "settings_prefs"
        const val THEME_KEY = "theme_key"
    }
}