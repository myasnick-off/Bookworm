package com.dev.miasnikoff.bookworm.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentEditBinding
import com.dev.miasnikoff.bookworm.ui._core.BaseFragment
import com.dev.miasnikoff.bookworm.ui._core.model.EditField
import com.dev.miasnikoff.bookworm.ui._core.model.UserModel
import com.dev.miasnikoff.bookworm.ui.info.InfoFragment
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
            val hasNoErrors = viewModel.checkFields(
                name = binding.nameEditText.text,
                date = binding.berthEditText.text,
                address = binding.addressEditText.text,
                email = binding.emailEditText.text
            )
            if (hasNoErrors) {
                viewModel.liveData.value?.let { user ->
                    openFragment(fragment = InfoFragment.newInstance(user))
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner, ::renderData)
    }

    private fun renderData(user: UserModel) {
        user.errorFields.forEach { field -> showFieldError(field) }
    }

    private fun showFieldError(field: EditField) {
        when (field) {
            EditField.NAME_FIELD -> binding.nameInputLayout
            EditField.BERTH_FIELD -> binding.berthInputLayout
            EditField.ADDRESS_FIELD -> binding.addressInputLayout
            EditField.EMAIL_FIELD -> binding.emailInputLayout
        }.apply {
            error = getString(field.messageResId)
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