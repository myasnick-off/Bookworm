package com.dev.miasnikoff.bookworm.main

import android.os.Bundle
import android.view.*
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.core.ui.BaseFragment
import com.dev.miasnikoff.bookworm.databinding.FragmentMainBinding
import com.dev.miasnikoff.bookworm.edit.EditFragment
import com.dev.miasnikoff.bookworm.list.VolumeListFragment
import com.dev.miasnikoff.bookworm.settings.SettingsFragment

class MainFragment : BaseFragment() {

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_settings -> openFragment(
                R.id.main_container,
                SettingsFragment.newInstance(),
                true
            )
            R.id.menu_profile -> openFragment(R.id.main_container, EditFragment.newInstance(), true)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initMenu() {
        super.initMenu()
        (requireActivity() as MainActivity).apply {
            setSupportActionBar(binding.mainToolbar)
            title = getString(R.string.app_name)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        setHasOptionsMenu(true)
    }

    override fun initView() {
        navigateToFragment(R.id.host_container, VolumeListFragment.newInstance())
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}