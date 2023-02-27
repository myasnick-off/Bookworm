package com.dev.miasnikoff.feature_tabs.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.dev.miasnikoff.core.model.Themes
import com.dev.miasnikoff.core.prefs.SettingsPrefsHelper
import com.dev.miasnikoff.core_di.ViewModelFactory
import com.dev.miasnikoff.core_navigation.viewModel
import com.dev.miasnikoff.core_ui.BaseFragment
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.FragmentSettingsBinding
import com.dev.miasnikoff.feature_tabs.di.TabsFeatureComponentViewModel
import javax.inject.Inject

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override lateinit var binding: FragmentSettingsBinding
    override val titleRes = R.string.settings
    override val isStickyToolbar = true
    override val hasBackButton = false

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override val viewModel: SettingsViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        viewModel<TabsFeatureComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        initMenu()
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    override fun initView() = with(binding) {
        super.initView()
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
                settingsPrefs.save(theme)
                AppCompatDelegate.setDefaultNightMode(theme.id)
            }
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}