package com.dev.miasnikoff.core_ui.extensions

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Property
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.PluralsRes
import androidx.core.content.getSystemService
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import java.util.*

fun View.showSnackBar(
    message: String,
    actionText: String? = null,
    length: Int = Snackbar.LENGTH_INDEFINITE,
    action: (View) -> Unit = {}
) {
    Snackbar.make(this, message, length).setAction(actionText, action).show()
}

fun View.vibrate(duration: Long) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val vibrator = this.context.getSystemService<Vibrator>()
        vibrator?.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.EFFECT_HEAVY_CLICK))
    }
}

fun View.hideSoftKeyboard() {
    this.findFocus()?.clearFocus()
    val imm = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.getLocalizedQuantityString(
    @PluralsRes id: Int,
    quantity: Int,
    vararg formatArgs: Any,
    locale: Locale = Locale("ru")
): String {
    return runCatching {
        var resources = this.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources = context.createConfigurationContext(configuration).resources
        resources.getQuantityString(id, quantity, *formatArgs)
    }.getOrDefault("")
}

fun ImageView.setImageById(imageRes: Int) {
    setImageDrawable(ResourcesCompat.getDrawable(context.resources, imageRes, context.theme))
}

fun View.createObjectAnimator(property: Property<View, Float>, animDuration: Long): ObjectAnimator =
    ObjectAnimator.ofFloat(this, property, 0f, 1f).apply {
        duration = animDuration
        start()
}