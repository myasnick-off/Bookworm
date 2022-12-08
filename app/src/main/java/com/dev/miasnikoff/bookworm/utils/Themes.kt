package com.dev.miasnikoff.bookworm.utils

import androidx.appcompat.app.AppCompatDelegate
import com.dev.miasnikoff.bookworm.R

enum class Themes(val id: Int, val textResId: Int) {
    AS_SYSTEM(id = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, textResId = R.string.system_mode),
    LIGHT_MODE(id = AppCompatDelegate.MODE_NIGHT_NO, textResId = R.string.light_mode),
    NIGHT_MODE(id = AppCompatDelegate.MODE_NIGHT_YES, textResId = R.string.night_mode);

    companion object {
        fun getTextResId(id: Int): Int {
            val theme = values().firstOrNull{ it.id == id }
            return theme?.textResId ?: R.string.system_mode
        }
    }
}