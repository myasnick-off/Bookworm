package com.dev.miasnikoff.core_ui

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment(layoutRes: Int) : Fragment(layoutRes) {

    protected abstract val binding: ViewBinding

    open fun initMenu() {}

    protected abstract fun initView()

    protected fun hideSoftKeyboard() {
        binding.root.findFocus()?.clearFocus()
        view?.let { view ->
            val imm =
                requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    protected fun showAlertDialog(
        titleRes: Int,
        positiveButtonRes: Int = R.string.yes,
        negativeButtonRes: Int = R.string.no,
        action: () -> Unit
    ) {
        AlertDialog.Builder(binding.root.context)
            .setTitle(titleRes)
            .setPositiveButton(positiveButtonRes) { _, _ -> action() }
            .setNegativeButton(negativeButtonRes) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}