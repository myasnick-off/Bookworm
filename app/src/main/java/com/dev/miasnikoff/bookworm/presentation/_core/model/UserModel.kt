package com.dev.miasnikoff.bookworm.presentation._core.model

import android.os.Parcelable
import com.dev.miasnikoff.bookworm.R
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class UserModel(
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

enum class EditField(val messageResId: Int) {
    NAME_FIELD(messageResId = R.string.name_error_message),
    BERTH_FIELD(messageResId = R.string.berth_error_message),
    ADDRESS_FIELD(messageResId = R.string.address_error_message),
    EMAIL_FIELD(messageResId = R.string.email_error_message)
}
