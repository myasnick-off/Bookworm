package com.dev.miasnikoff.bookworm.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.core.ui.BaseFragment
import com.dev.miasnikoff.bookworm.databinding.FragmentEditBinding
import com.dev.miasnikoff.bookworm.info.InfoFragment
import com.dev.miasnikoff.bookworm.model.EditField
import com.dev.miasnikoff.bookworm.model.UserEntity
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class EditFragment : BaseFragment() {

    private lateinit var _binding: FragmentEditBinding
    override val binding: FragmentEditBinding
        get() = _binding

    private val viewModel: EditViewModel by lazy {
        ViewModelProvider(this)[EditViewModel::class.java]
    }

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
        initView()
        initMenu()
        initViewModel()
    }

    override fun initView() = with(binding) {
        root.setOnClickListener { hideSoftKeyboard() }
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
            //viewModel.checkFields()
            val isNameValid = viewModel.checkName(binding.nameEditText.text)
            val isBerthDateValid = viewModel.checkBerthDate(binding.berthEditText.text)
            val isAddressValid = viewModel.checkAddress(binding.addressEditText.text)
            val isEmailValid = viewModel.checkEmail(binding.emailEditText.text)
            if (isNameValid && isBerthDateValid && isAddressValid && isEmailValid) {
                viewModel.liveData.value?.let { user ->
                    navigateToFragment(R.id.host_container, InfoFragment.newInstance(user))
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner, ::renderData)
    }

    private fun renderData(user: UserEntity) {
        user.errorFields.forEach { field ->
            when (field) {
                EditField.NAME_FIELD -> handleNameError()
                EditField.BERTH_FIELD -> handleBerthDateError()
                EditField.ADDRESS_FIELD -> handleAddressError()
                EditField.EMAIL_FIELD -> handleEmailError()
            }
        }
    }

    private fun handleNameError() {
        binding.nameInputLayout.apply {
            error = getString(R.string.name_error_message)
            isErrorEnabled = true
        }
    }

    private fun handleBerthDateError() {
        binding.berthInputLayout.apply {
            error = getString(R.string.berth_error_message)
            isErrorEnabled = true
        }
    }

    private fun handleAddressError() {
        binding.addressInputLayout.apply {
            error = getString(R.string.address_error_message)
            isErrorEnabled = true
        }
    }

    private fun handleEmailError() {
        binding.emailInputLayout.apply {
            error = getString(R.string.email_error_message)
            isErrorEnabled = true
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText(getString(R.string.berth_date))
            .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
            .build()
        datePickerDialog.addOnPositiveButtonClickListener {
            datePickerDialog.selection?.let { date -> setBerthDate(date) }
        }
        datePickerDialog.show(parentFragmentManager, null)
    }

    private fun setBerthDate(date: Long) {
        binding.berthEditText.setText(viewModel.dateFormat.format(Date(date)))
    }

    companion object {
        fun newInstance(): EditFragment = EditFragment()
    }
}