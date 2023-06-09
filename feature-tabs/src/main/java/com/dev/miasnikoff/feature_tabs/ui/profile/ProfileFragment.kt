package com.dev.miasnikoff.feature_tabs.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.dev.miasnikoff.core.model.UserModel
import com.dev.miasnikoff.core.prefs.UserPrefsHelper
import com.dev.miasnikoff.core_di.ViewModelFactory
import com.dev.miasnikoff.core_navigation.DeepLink
import com.dev.miasnikoff.core_navigation.viewModel
import com.dev.miasnikoff.core_ui.BaseFragment
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.FragmentProfileBinding
import com.dev.miasnikoff.feature_tabs.di.TabsFeatureComponentViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override lateinit var binding: FragmentProfileBinding
    override val titleRes = R.string.profile
    override val isStickyToolbar = true
    override val hasBackButton = false
    private lateinit var userPrefsHelper: UserPrefsHelper
    private lateinit var user: UserModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override val viewModel: ProfileViewModel by viewModels { viewModelFactory }

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    override fun onAttach(context: Context) {
        viewModel<TabsFeatureComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        userPrefsHelper = UserPrefsHelper(requireContext())
        user = userPrefsHelper.user
        initView()
        initMenu()
    }

    override fun initView() = with(binding) {
        super.initView()
        nameText.text = user.name
        berthText.text = user.berthDate?.let { dateFormat.format(it) }
        addressText.text = user.address
        emailText.text = user.email
        editButton.setOnClickListener { navigateToEdit() }
        loginButton.setOnClickListener { navigateToLogin() }
    }

    private fun navigateToEdit() {
        val direction = ProfileFragmentDirections.actionProfileFragmentToEditFragment(user)
        viewModel.navigate(direction)
    }

    private fun navigateToLogin() {
        viewModel.navigate(link = DeepLink.LOGIN_LINK)
    }
}