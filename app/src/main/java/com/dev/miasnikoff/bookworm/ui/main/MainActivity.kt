package com.dev.miasnikoff.bookworm.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ActivityMainBinding
import com.dev.miasnikoff.bookworm.ui.home.HomeViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragmentViewModels()
        if (savedInstanceState == null) {
            navigateToFragment(MainFragment.newInstance())
        }
    }

    override fun onBackPressed() {
        val mainFragment = supportFragmentManager.findFragmentById(R.id.main_container)
        (mainFragment as? BackPressMonitor)?.let { backPressMonitor ->
            if (!backPressMonitor.onBackPressed()) {
                super.onBackPressed()
            }
        } ?: super.onBackPressed()
    }

    private fun initFragmentViewModels() {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}