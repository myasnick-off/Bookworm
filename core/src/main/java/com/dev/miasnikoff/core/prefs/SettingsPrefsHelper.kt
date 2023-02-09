package com.dev.miasnikoff.core.prefs

import android.content.Context
import com.dev.miasnikoff.core.model.Themes

class SettingsPrefsHelper(context: Context) {

    private val prefs = context.getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE)

    val appTheme: Themes
        get() {
            return try {
                val name = prefs.getString(THEME_KEY, Themes.AS_SYSTEM.name)
                name?.let { Themes.valueOf(it) } ?: Themes.AS_SYSTEM
            } catch (ex: Exception) {
                Themes.AS_SYSTEM
            }
        }

    fun save(appTheme: Themes) {
        prefs.edit().putString(THEME_KEY, appTheme.name).apply()
    }

    companion object {
        private const val SETTINGS_PREFS = "settings_prefs"
        private const val THEME_KEY = "theme_key"
    }
}