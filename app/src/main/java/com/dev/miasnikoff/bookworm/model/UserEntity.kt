package com.dev.miasnikoff.bookworm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class UserEntity(
    val name: String = "",
    val berthDate: Date? = null,
    val address: String = "",
    val email: String = "",
    val errorFields: List<EditField> = listOf()
) : Parcelable {

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    override fun toString(): String {
        return "name: $name\nberth date: ${berthDate?.let { dateFormat.format(it) }}\nAddress: $address\nE-mail: $email"
    }
}

enum class EditField {
    NAME_FIELD,
    BERTH_FIELD,
    ADDRESS_FIELD,
    EMAIL_FIELD
}
