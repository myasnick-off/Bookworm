package com.dev.miasnikoff.bookworm.utils.extensions

import android.app.Activity
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    message: String,
    actionText: String,
    length: Int = Snackbar.LENGTH_INDEFINITE,
    action: (View) -> Unit
) {
    Snackbar.make(this, message, length).setAction(actionText, action).show()
}

@RequiresApi(Build.VERSION_CODES.Q)
fun View.vibrate(duration: Long) {
    val vibrator = this.context.getSystemService<Vibrator>()
    vibrator?.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.EFFECT_HEAVY_CLICK))
}

fun View.hideSoftKeyboard() {
    this.findFocus()?.clearFocus()
    val imm = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
}