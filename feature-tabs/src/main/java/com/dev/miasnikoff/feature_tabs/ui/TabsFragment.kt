package com.dev.miasnikoff.feature_tabs.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dev.miasnikoff.core.extensions.viewModel
import com.dev.miasnikoff.core_di.annotations.FlowNavHolder
import com.dev.miasnikoff.core_di.findFeatureExternalDeps
import com.dev.miasnikoff.core_navigation.navigator.NavigatorHolder
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.BaseFragment
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.FragmentTabsBinding
import com.dev.miasnikoff.feature_tabs.di.TabsFeatureComponentExternalDepsProvider
import com.dev.miasnikoff.feature_tabs.di.TabsFeatureComponentViewModel
import javax.inject.Inject

class TabsFragment : BaseFragment(R.layout.fragment_tabs) {

    @Inject
    @FlowNavHolder
    lateinit var navigatorHolder: NavigatorHolder<NavController>

    @Inject
    lateinit var flowRouter: FlowRouter

    override lateinit var binding: FragmentTabsBinding
    private lateinit var navController: NavController

    override fun onAttach(context: Context) {
        TabsFeatureComponentExternalDepsProvider.featureExternalDeps = findFeatureExternalDeps()
        viewModel<TabsFeatureComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTabsBinding.bind(view)
        initView()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.bind(navController)
    }

    override fun onPause() {
        navigatorHolder.unbind()
        super.onPause()
    }

    override fun initView() {
        val navHost = childFragmentManager.findFragmentById(R.id.tabs_container) as NavHostFragment
        navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }
}