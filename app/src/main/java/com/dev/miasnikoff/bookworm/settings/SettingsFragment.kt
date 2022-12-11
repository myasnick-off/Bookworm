package com.dev.miasnikoff.bookworm.settings

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.core.BaseFragment
import com.dev.miasnikoff.bookworm.databinding.FragmentSettingsBinding
import com.dev.miasnikoff.bookworm.main.MainActivity
import com.dev.miasnikoff.bookworm.utils.PrefsHandler
import com.dev.miasnikoff.bookworm.utils.Themes

class SettingsFragment : BaseFragment() {

    private lateinit var _binding: FragmentSettingsBinding
    override val binding: FragmentSettingsBinding
        get() = _binding

    private lateinit var themePrefs: PrefsHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        when(item.itemId) {
            android.R.id.home -> requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initMenu() {
        super.initMenu()
        (requireActivity() as MainActivity).apply {
            setSupportActionBar(binding.settingsToolbar)
            title = getString(R.string.settings)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
    }

    override fun initView() = with(binding) {
        themePrefs = PrefsHandler(requireContext())
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown_menu,
            Themes.values().map { getString(it.textResId) }
        )
        val themeResId = Themes.getTextResId(themePrefs.loadInt(PrefsHandler.THEME_KEY))

        selectAppThemeText.setText(themeResId)
        selectAppThemeText.setAdapter(adapter)
        selectAppThemeText.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val mode = when (position) {
                    0 -> Themes.AS_SYSTEM.id
                    1 -> Themes.LIGHT_MODE.id
                    2 -> Themes.NIGHT_MODE.id
                    else -> Themes.AS_SYSTEM.id
                }
                if (mode != themePrefs.loadInt(PrefsHandler.THEME_KEY)) {
                    themePrefs.save(PrefsHandler.THEME_KEY, mode)
                    AppCompatDelegate.setDefaultNightMode(mode)
                }
            }
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}