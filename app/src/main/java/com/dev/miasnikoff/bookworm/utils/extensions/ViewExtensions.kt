package com.dev.miasnikoff.bookworm.utils.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    message: String,
    actionText: String,
    length: Int = Snackbar.LENGTH_INDEFINITE,
    action: (View) -> Unit
) {
    Snackbar
        .make(this, message, length)
        .setAction(actionText, action)
        .show()
}