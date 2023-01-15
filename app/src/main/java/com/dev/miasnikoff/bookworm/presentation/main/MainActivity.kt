package com.dev.miasnikoff.bookworm.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}