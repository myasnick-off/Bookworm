package com.dev.miasnikoff.feature_tabs.ui.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dev.miasnikoff.core.model.Themes
import com.dev.miasnikoff.core.prefs.SettingsPrefsHelper
import com.dev.miasnikoff.core_ui.BaseFragment
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        initMenu()
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initMenu() {
        super.initMenu()
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.settingsToolbar)
            title = getString(com.dev.miasnikoff.core_ui.R.string.settings)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
    }

    override fun initView() = with(binding) {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown_menu,
            Themes.values().map { getString(it.textResId) }
        )
        val settingsPrefs = SettingsPrefsHelper(requireContext())
        selectAppThemeText.setText(settingsPrefs.appTheme.textResId)
        selectAppThemeText.setAdapter(adapter)
        selectAppThemeText.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val theme = when (position) {
                    0 -> Themes.AS_SYSTEM
                    1 -> Themes.LIGHT_MODE
                    2 -> Themes.NIGHT_MODE
                    else -> Themes.AS_SYSTEM
                }
                if (theme != settingsPrefs.appTheme) {
                    settingsPrefs.save(theme)
                    AppCompatDelegate.setDefaultNightMode(theme.id)
                }
            }
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}