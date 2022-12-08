package com.dev.miasnikoff.bookworm.edit

import android.text.Editable
import com.dev.miasnikoff.bookworm.model.UserEntity

interface EditPresenter {
    val user: UserEntity
    fun attachView(view: EditView)
    fun detachView()
    fun checkName(name: Editable?): Boolean
    fun checkAddress(address: Editable?): Boolean
    fun checkBerthDate(date: Editable?): Boolean
    fun checkEmail(email: Editable?): Boolean
    fun setBerthDate(date: Long)
}