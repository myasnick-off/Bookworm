package com.dev.miasnikoff.bookworm.presentation.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.ListFragment
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentMainBinding
import com.dev.miasnikoff.bookworm.presentation._core.BaseFragment
import com.dev.miasnikoff.bookworm.presentation.edit.EditFragment
import com.dev.miasnikoff.bookworm.presentation.home.HomeFragment
import com.dev.miasnikoff.bookworm.presentation.list.VolumeListFragment
import com.dev.miasnikoff.bookworm.presentation.login.LoginFragment
import com.dev.miasnikoff.bookworm.presentation.settings.SettingsFragment

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
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> openFragment(
                R.id.main_container,
                SettingsFragment.newInstance(),
                true
            )
            R.id.menu_profile -> openFragment(R.id.main_container, EditFragment.newInstance(), true)
        }
        return super.onOptionsItemSelected(item)
    }*/

    override fun initMenu() {
        super.initMenu()
        /*(requireActivity() as MainActivity).apply {
            setSupportActionBar(binding.mainToolbar)
            title = getString(R.string.app_name)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        setHasOptionsMenu(true)*/
    }

    override fun initView() {
        navigateToFragment(fragment = HomeFragment.newInstance())
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    navigateToFragment(fragment = HomeFragment.newInstance())
                    true
                }
                R.id.action_search -> {
                    navigateToFragment(fragment = SettingsFragment.newInstance(), isAddToBackStack = true)
                    true
                }
                R.id.action_favorite -> {
                    navigateToFragment(fragment = VolumeListFragment.newInstance(), isAddToBackStack = true)
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