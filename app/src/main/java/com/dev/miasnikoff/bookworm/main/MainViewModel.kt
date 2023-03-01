package com.dev.miasnikoff.bookworm.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.core_navigation.router.GlobalRouter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val router: GlobalRouter) : ViewModel() {

    private val slashGraphId = com.dev.miasnikoff.splash.R.navigation.splash_nav_graph
    private val mainGraphId = R.navigation.main_nav_graph

    fun startNavigationWithSplash() {
        setNavigationGraph(slashGraphId)
        viewModelScope.launch {
            delay(SPLASH_SCREEN_SHOW_TIME)
            setNavigationGraph(mainGraphId)
        }
    }

    private fun setNavigationGraph(graphResId: Int) {
        router.setNewGraph(graphResId)
    }

    companion object {
        private const val SPLASH_SCREEN_SHOW_TIME = 2000L
    }
}