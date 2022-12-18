package com.dev.miasnikoff.bookworm.utils.extensions

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
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