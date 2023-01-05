package com.dev.miasnikoff.bookworm.presentation.search

interface SearchClickListener {
    fun onSearchClick(phrase: String)
    fun onDialogDismiss()
}