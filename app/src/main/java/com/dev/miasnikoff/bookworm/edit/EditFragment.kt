package com.dev.miasnikoff.bookworm.edit

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.core.BaseFragment
import com.dev.miasnikoff.bookworm.databinding.FragmentEditBinding
import com.dev.miasnikoff.bookworm.info.InfoFragment
import com.google.android.material.datepicker.MaterialDatePicker

class EditFragment : BaseFragment(), EditView {

    private lateinit var _binding: FragmentEditBinding
    override val binding: FragmentEditBinding
        get() = _binding

    private val presenter: EditPresenter = EditPresenterImpl()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun initView() = with(binding) {
        root.setOnClickListener {
            hideSoftKeyboard()
        }
        nameEditText.doOnTextChanged { _, _, _, _ ->
            nameInputLayout.isErrorEnabled = false
        }
        berthEditText.doOnTextChanged { _, _, _, _ ->
            berthInputLayout.isErrorEnabled = false
        }
        addressEditText.doOnTextChanged { _, _, _, _ ->
            addressInputLayout.isErrorEnabled = false
        }
        emailEditText.doOnTextChanged { _, _, _, _ ->
            emailInputLayout.isErrorEnabled = false
        }
        berthInputLayout.setStartIconOnClickListener {
            berthInputLayout.isErrorEnabled = false
            hideSoftKeyboard()
            showDatePickerDialog()
        }
        saveButton.setOnClickListener {
            hideSoftKeyboard()
            val isNameValid = presenter.checkName(binding.nameEditText.text)
            val isBerthDateValid = presenter.checkBerthDate(binding.berthEditText.text)
            val isAddressValid = presenter.checkAddress(binding.addressEditText.text)
            val isEmailValid = presenter.checkEmail(binding.emailEditText.text)
            if (isNameValid && isBerthDateValid && isAddressValid && isEmailValid) {
                navigateToFragment(InfoFragment.newInstance(presenter.user))
            }
        }
    }

    override fun showBerthDate(dateString: String) {
        binding.berthEditText.setText(dateString)
    }

    override fun handleNameError(isError: Boolean) {
        binding.nameInputLayout.apply {
            error = getString(R.string.name_error_message)
            isErrorEnabled = isError
        }
    }

    override fun handleBerthDateError(isError: Boolean) {
        binding.berthInputLayout.apply {
            error = getString(R.string.berth_error_message)
            isErrorEnabled = isError
        }
    }

    override fun handleAddressError(isError: Boolean) {
        binding.addressInputLayout.apply {
            error = getString(R.string.address_error_message)
            isErrorEnabled = isError
        }
    }

    override fun handleEmailError(isError: Boolean) {
        binding.emailInputLayout.apply {
            error = getString(R.string.email_error_message)
            isErrorEnabled = isError
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText(getString(R.string.berth_date))
            .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
            .build()
        datePickerDialog.addOnPositiveButtonClickListener {
            datePickerDialog.selection?.let { date ->
                presenter.setBerthDate(date)
            }
        }
        datePickerDialog.show(parentFragmentManager, null)
    }

    private fun hideSoftKeyboard() {
        binding.root.findFocus()?.clearFocus()
        view?.let { view ->
            val imm =
                requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    companion object {
        fun newInstance(): EditFragment = EditFragment()
    }
}