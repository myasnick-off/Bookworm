package com.dev.miasnikoff.bookworm.edit

import android.text.Editable
import com.dev.miasnikoff.bookworm.model.UserEntity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EditPresenterImpl : EditPresenter {

    private var view: EditView? = null
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private var userEntity = UserEntity()
    override val user: UserEntity
        get() = userEntity

    override fun attachView(view: EditView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun checkName(name: Editable?): Boolean {
        return if (name.isNullOrBlank()) {
            view?.handleNameError(isError = true)
            false
        } else {
            userEntity = userEntity.copy(name = name.toString())
            view?.handleNameError(isError = false)
            true
        }
    }

    override fun checkAddress(address: Editable?): Boolean {
        return if (address.isNullOrBlank()) {
            view?.handleAddressError(isError = true)
            false
        } else {
            userEntity = userEntity.copy(address = address.toString())
            view?.handleAddressError(isError = false)
            true
        }
    }

    override fun checkBerthDate(date: Editable?): Boolean {
        if (!date.isNullOrBlank()) {
            try {
                val now = Date()
                val berthDate = dateFormat.parse(date.toString())
                if (berthDate < now) {
                    userEntity = userEntity.copy(berthDate = berthDate)
                    view?.handleBerthDateError(isError = false)
                    return true
                }
            } catch (e: ParseException) {
            }
        }
        view?.handleBerthDateError(isError = true)
        return false
    }

    override fun checkEmail(email: Editable?): Boolean {
        return if (email.isNullOrBlank() ||
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()
        ) {
            view?.handleEmailError(isError = true)
            false
        } else {
            userEntity = userEntity.copy(email = email.toString())
            view?.handleEmailError(isError = false)
            true
        }
    }

    override fun setBerthDate(date: Long) {
        view?.showBerthDate(dateFormat.format(Date(date)))
    }
}