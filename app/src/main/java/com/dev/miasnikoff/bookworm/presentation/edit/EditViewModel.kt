package com.dev.miasnikoff.bookworm.presentation.edit

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.bookworm.presentation._core.model.EditField
import com.dev.miasnikoff.bookworm.presentation._core.model.UserModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EditViewModel : ViewModel() {

    private var _liveData: MutableLiveData<UserModel> = MutableLiveData(UserModel())
    val liveData: LiveData<UserModel> get() = _liveData

    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private var user = UserModel()

    fun checkFields(
        name: Editable?,
        date: Editable?,
        address: Editable?,
        email: Editable?
    ): Boolean {
        checkName(name)
        checkBerthDate(date)
        checkAddress(address)
        checkEmail(email)
        _liveData.value = user
        return user.errorFields.isEmpty()
    }

    private fun checkName(name: Editable?) {
        val errorFields = user.errorFields.toMutableList()
        user = if (name.isNullOrBlank()) {
            errorFields.add(EditField.NAME_FIELD)
            user.copy(errorFields = errorFields)
        } else {
            errorFields.removeAll { it == EditField.NAME_FIELD }
            user.copy(name = name.toString(), errorFields = errorFields)
        }
    }

    private fun checkAddress(address: Editable?) {
        val errorFields = user.errorFields.toMutableList()
        user = if (address.isNullOrBlank()) {
            errorFields.add(EditField.ADDRESS_FIELD)
            user.copy(errorFields = errorFields)
        } else {
            errorFields.removeAll { it == EditField.ADDRESS_FIELD }
            user.copy(address = address.toString(), errorFields = errorFields)
        }
    }

    private fun checkBerthDate(date: Editable?) {
        val errorFields = user.errorFields.toMutableList()
        if (!date.isNullOrBlank()) {
            try {
                val now = Date()
                val berthDate = dateFormat.parse(date.toString())
                if (berthDate < now) {
                    errorFields.removeAll { it == EditField.BERTH_FIELD }
                    user = user.copy(berthDate = berthDate, errorFields = errorFields)
                    return
                }
            } catch (e: ParseException) {
            }
        }
        errorFields.add(EditField.BERTH_FIELD)
        user = user.copy(errorFields = errorFields)
    }

    private fun checkEmail(email: Editable?) {
        val errorFields = user.errorFields.toMutableList()
        user = if (email.isNullOrBlank() ||
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()
        ) {
            errorFields.add(EditField.EMAIL_FIELD)
            user.copy(errorFields = errorFields)
        } else {
            errorFields.removeAll { it == EditField.EMAIL_FIELD }
            user.copy(email = email.toString(), errorFields = errorFields)
        }
    }
}