package com.dev.miasnikoff.bookworm.edit

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.bookworm.model.EditField
import com.dev.miasnikoff.bookworm.model.UserEntity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EditViewModel : ViewModel() {

    private var _liveData: MutableLiveData<UserEntity> = MutableLiveData(UserEntity())
    val liveData: LiveData<UserEntity> get() = _liveData

    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private var userEntity = UserEntity()
    val user: UserEntity
        get() = userEntity

    fun checkName(name: Editable?): Boolean {
        return if (name.isNullOrBlank()) {
            liveData.value?.let { user ->
                val errorFields = user.errorFields.toMutableList()
                errorFields.add(EditField.NAME_FIELD)
                _liveData.value = user.copy(errorFields = errorFields)
            }
            false
        } else {
            liveData.value?.let { user ->
                val errorFields = user.errorFields.toMutableList()
                errorFields.remove(EditField.NAME_FIELD)
                _liveData.value = user.copy(name = name.toString(), errorFields = errorFields)
            }
            true
        }
    }

    fun checkAddress(address: Editable?): Boolean {
        return if (address.isNullOrBlank()) {
            liveData.value?.let { user ->
                val errorFields = user.errorFields.toMutableList()
                errorFields.add(EditField.ADDRESS_FIELD)
                _liveData.value = user.copy(errorFields = errorFields)
            }
            false
        } else {
            liveData.value?.let { user ->
                val errorFields = user.errorFields.toMutableList()
                errorFields.remove(EditField.ADDRESS_FIELD)
                _liveData.value = user.copy(address = address.toString(), errorFields = errorFields)
            }
            true
        }
    }

    fun checkBerthDate(date: Editable?): Boolean {
        if (!date.isNullOrBlank()) {
            try {
                val now = Date()
                val berthDate = dateFormat.parse(date.toString())
                if (berthDate < now) {
                    liveData.value?.let { user ->
                        val errorFields = user.errorFields.toMutableList()
                        errorFields.remove(EditField.BERTH_FIELD)
                        _liveData.value = user.copy(berthDate = berthDate, errorFields = errorFields)
                    }
                    return true
                }
            } catch (e: ParseException) {
            }
        }
        liveData.value?.let { user ->
            val errorFields = user.errorFields.toMutableList()
            errorFields.add(EditField.BERTH_FIELD)
            _liveData.value = user.copy(errorFields = errorFields)
        }
        return false
    }

    fun checkEmail(email: Editable?): Boolean {
        return if (email.isNullOrBlank() ||
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()
        ) {
            liveData.value?.let { user ->
                val errorFields = user.errorFields.toMutableList()
                errorFields.add(EditField.EMAIL_FIELD)
                _liveData.value = user.copy(errorFields = errorFields)
            }
            false
        } else {
            liveData.value?.let { user ->
                val errorFields = user.errorFields.toMutableList()
                errorFields.remove(EditField.EMAIL_FIELD)
                _liveData.value = user.copy(email = email.toString(), errorFields = errorFields)
            }
            true
        }
    }

    fun checkFields(name: Editable?, date: Editable?, address: Editable?, email: Editable?): Boolean {
        TODO("Not yet implemented")
    }
}