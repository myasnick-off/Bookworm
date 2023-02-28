package com.dev.miasnikoff.core.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.customDateFormat(pattern: String): String {
    val sourceDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val resultDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return try {
        val date = sourceDateFormat.parse(this)
        date?.let { resultDateFormat.format(it) } ?: this
    } catch (e: Exception) {
        this
    }
}