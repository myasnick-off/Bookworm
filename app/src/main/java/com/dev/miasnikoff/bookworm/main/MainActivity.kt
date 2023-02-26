package com.dev.miasnikoff.bookworm.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dev.miasnikoff.bookworm.App
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.core_di.FeatureExternalDependenciesContainer
import com.dev.miasnikoff.core_di.FeatureExternalDepsProvider
import com.dev.miasnikoff.core_di.ViewModelFactory
import com.dev.miasnikoff.core_di.annotations.GlobalNavHolder
import com.dev.miasnikoff.core_navigation.navigator.NavigatorHolder
import com.dev.miasnikoff.feature_auth_api.AuthFeatureApi
import com.dev.miasnikoff.feature_tabs_api.TabsFeatureApi
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), FeatureExternalDepsProvider {

    @Inject
    override lateinit var externalDeps: FeatureExternalDependenciesContainer

    @Inject
    lateinit var tabsFeatureApi: TabsFeatureApi

    @Inject
    lateinit var authFeatureApi: AuthFeatureApi

    @Inject
    @GlobalNavHolder
    lateinit var navigatorHolder: NavigatorHolder<NavController>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.startNavigationWithSplash()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.bind(getRootNavController())
    }

    override fun onPause() {
        navigatorHolder.unbind()
        super.onPause()
    }

    private fun getRootNavController(): NavController {
        val navHost = supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        return navHost.navController
    }
}