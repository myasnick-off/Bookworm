package com.dev.miasnikoff.bookworm.utils

import android.content.Context
import com.dev.miasnikoff.bookworm.ui._core.model.UserModel
import java.text.SimpleDateFormat
import java.util.*

class UserPrefsHelper(context: Context) {

    private val prefs = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    val user: UserModel
        get() = UserModel(
            name = loadString(NAME_KEY),
            berthDate = dateFormat.parseOrNull(loadString(BERTH_KEY)),
            address = loadString(ADDRESS_KEY),
            email = loadString(EMAIL_KEY)
        )

    fun saveUser(name: String, berthDate: Date? = null, address: String = "", email: String) {
        val user = UserModel(
            name = name,
            berthDate = berthDate,
            address = address,
            email = email
        )
        saveUser(user)
    }

    fun saveUser(user: UserModel) {
        val userMap = mapOf(
            NAME_KEY to user.name,
            BERTH_KEY to (user.berthDate?.let { dateFormat.format(it) } ?: ""),
            ADDRESS_KEY to user.address,
            EMAIL_KEY to user.email
        )
        userMap.forEach { entry ->
            saveString(entry.key, entry.value)
        }
    }

    private fun loadString(key: String): String {
        return try {
            prefs.getString(key, "") ?: ""
        } catch (ex: Exception) {
            ""
        }
    }

    private fun saveString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    private fun SimpleDateFormat.parseOrNull(dateString: String): Date? {
        return try {
            this.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private const val USER_PREFS = "user_prefs"
        private const val NAME_KEY = "name_key"
        private const val ADDRESS_KEY = "address_key"
        private const val BERTH_KEY = "berth_key"
        private const val EMAIL_KEY = "email_key"
    }
}