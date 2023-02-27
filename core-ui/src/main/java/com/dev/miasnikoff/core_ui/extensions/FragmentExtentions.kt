package com.dev.miasnikoff.core_ui.extensions

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.dev.miasnikoff.core_ui.R
import com.google.android.material.datepicker.MaterialDatePicker

fun Fragment.showAlertDialog(
    titleRes: Int,
    positiveButtonRes: Int = R.string.yes,
    negativeButtonRes: Int = R.string.no,
    action: () -> Unit
) {
    AlertDialog.Builder(requireContext())
        .setTitle(titleRes)
        .setPositiveButton(positiveButtonRes) { _, _ -> action() }
        .setNegativeButton(negativeButtonRes) { dialog, _ -> dialog.dismiss() }
        .create()
        .show()
}

fun Fragment.showDatePickerDialog(titleRes: Int, positiveAction: (Long) -> Unit) {
    val datePickerDialog = MaterialDatePicker.Builder
        .datePicker()
        .setTitleText(getString(titleRes))
        .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
        .build()
    datePickerDialog.addOnPositiveButtonClickListener {
        datePickerDialog.selection?.let { date -> positiveAction(date) }
    }
    datePickerDialog.show(parentFragmentManager, null)
}