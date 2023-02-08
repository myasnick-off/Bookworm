package com.dev.miasnikoff.bookworm.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dev.miasnikoff.bookworm.App
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentTabsBinding
import com.dev.miasnikoff.bookworm.di.FlowNavHolder
import com.dev.miasnikoff.bookworm.ui._core.BaseFragment
import com.dev.miasnikoff.bookworm.utils.navigation.navholder.NavigatorHolder
import javax.inject.Inject

class TabsFragment : BaseFragment(R.layout.fragment_tabs) {

    @Inject
    @FlowNavHolder
    lateinit var navigatorHolder: NavigatorHolder<NavController>

    override lateinit var binding: FragmentTabsBinding
    private lateinit var navController: NavController

    override fun onAttach(context: Context) {
        App.appInstance.appComponent.inject(this)
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