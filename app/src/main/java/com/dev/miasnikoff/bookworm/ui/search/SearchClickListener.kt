package com.dev.miasnikoff.bookworm.ui.search

interface SearchClickListener {
    fun onSearchClick(phrase: String)
    fun onDialogDismiss()
}