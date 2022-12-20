package com.dev.miasnikoff.bookworm.search

interface SearchClickListener {
    fun onSearchClick(phrase: String)
    fun onDialogDismiss()
}