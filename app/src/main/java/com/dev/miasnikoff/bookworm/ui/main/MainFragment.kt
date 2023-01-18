package com.dev.miasnikoff.bookworm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentMainBinding
import com.dev.miasnikoff.bookworm.ui._core.BaseFragment
import com.dev.miasnikoff.bookworm.ui.home.HomeFragment
import com.dev.miasnikoff.bookworm.ui.list.BookListFragment
import com.dev.miasnikoff.bookworm.ui.login.LoginFragment
import com.dev.miasnikoff.bookworm.ui.settings.SettingsFragment

class MainFragment : BaseFragment(), BackPressMonitor {

    private lateinit var _binding: FragmentMainBinding
    override val binding: FragmentMainBinding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            initView()
        }
        initMenu()
        initBottomNavigation()
    }

    override fun initView() {
        navigateToFragment(fragment = HomeFragment.newInstance())
    }

    private fun initBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    true
                }
                R.id.action_search -> {
                    navigateToFragment(fragment = SettingsFragment.newInstance(), isAddToBackStack = true)
                    true
                }
                R.id.action_favorite -> {
                    navigateToFragment(fragment = BookListFragment.newInstance(), isAddToBackStack = true)
                    true
                }
                R.id.action_profile -> {
                    navigateToFragment(fragment = LoginFragment.newInstance(), isAddToBackStack = true)
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed(): Boolean {
        return if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            binding.bottomNavigation.selectedItemId = R.id.action_home
            true

        } else false
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}