package com.dev.miasnikoff.feature_tabs.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dev.miasnikoff.core.model.EditField
import com.dev.miasnikoff.core.model.UserModel
import com.dev.miasnikoff.core.prefs.UserPrefsHelper
import com.dev.miasnikoff.core_di.ViewModelFactory
import com.dev.miasnikoff.core_navigation.viewModel
import com.dev.miasnikoff.core_ui.BaseFragment
import com.dev.miasnikoff.core_ui.extensions.hideSoftKeyboard
import com.dev.miasnikoff.core_ui.extensions.showDatePickerDialog
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.FragmentEditBinding
import com.dev.miasnikoff.feature_tabs.di.TabsFeatureComponentViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EditFragment : BaseFragment(R.layout.fragment_edit) {

    override lateinit var binding: FragmentEditBinding
    override val titleRes = R.string.profile_edit
    override val isStickyToolbar = true
    private lateinit var userPrefsHelper: UserPrefsHelper

    private val args: EditFragmentArgs by navArgs()
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override val viewModel: EditViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        viewModel<TabsFeatureComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditBinding.bind(view)
        userPrefsHelper = UserPrefsHelper(requireContext())
        initView()
        initMenu()
        initViewModel()
    }

    override fun initView() = with(binding) {
        super.initView()
        root.setOnClickListener { root.hideSoftKeyboard() }
        nameEditText.setText(args.user.name)
        nameEditText.doOnTextChanged { _, _, _, _ ->
            nameInputLayout.isErrorEnabled = false
        }
        berthEditText.setText(args.user.berthDate?.let { dateFormat.format(it) } ?: "")
        berthEditText.doOnTextChanged { _, _, _, _ ->
            berthInputLayout.isErrorEnabled = false
        }
        addressEditText.setText(args.user.address)
        addressEditText.doOnTextChanged { _, _, _, _ ->
            addressInputLayout.isErrorEnabled = false
        }
        emailEditText.setText(args.user.email)
        emailEditText.doOnTextChanged { _, _, _, _ ->
            emailInputLayout.isErrorEnabled = false
        }
        berthInputLayout.setStartIconOnClickListener {
            berthInputLayout.isErrorEnabled = false
            root.hideSoftKeyboard()
            showDatePickerDialog(R.string.berth_date) { date -> setBerthDate(date) }
        }
        saveButton.setOnClickListener {
            root.hideSoftKeyboard()
            val hasNoErrors = viewModel.checkFields(
                name = binding.nameEditText.text,
                date = binding.berthEditText.text,
                address = binding.addressEditText.text,
                email = binding.emailEditText.text
            )
            if (hasNoErrors) {
                viewModel.liveData.value?.let { user ->
                    userPrefsHelper.saveUser(user)
                    viewModel.back()
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

    private fun setBerthDate(date: Long) {
        binding.berthEditText.setText(viewModel.dateFormat.format(Date(date)))
    }
}