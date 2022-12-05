package com.dev.miasnikoff.bookworm.edit

interface EditView {
    fun showBerthDate(dateString: String)
    fun handleNameError(isError: Boolean)
    fun handleBerthDateError(isError: Boolean)
    fun handleAddressError(isError: Boolean)
    fun handleEmailError(isError: Boolean)
}