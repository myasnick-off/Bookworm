package com.dev.miasnikoff.bookworm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dev.miasnikoff.bookworm.App
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.core_di.FeatureExternalDependenciesContainer
import com.dev.miasnikoff.core_di.FeatureExternalDepsProvider
import com.dev.miasnikoff.core_di.annotations.GlobalNavHolder
import com.dev.miasnikoff.core_navigation.navigator.NavigatorHolder
import com.dev.miasnikoff.core_navigation.router.GlobalRouter
import com.dev.miasnikoff.feature_tabs_api.TabsFeatureApi
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), FeatureExternalDepsProvider {

    @Inject
    override lateinit var externalDeps: FeatureExternalDependenciesContainer

    @Inject
    lateinit var tabsFeatureApi: TabsFeatureApi

    @Inject
    @GlobalNavHolder
    lateinit var navigatorHolder: NavigatorHolder<NavController>

    @Inject
    lateinit var globalRouter: GlobalRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        //setContentView(ActivityMainBinding.inflate(layoutInflater).root)

        //globalRouter.setNewFlow(tabsFeatureApi.getTabsDestination())
        globalRouter.setNewFlow(getTabsDestination())
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
        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        return navHost.navController
    }

    private fun getTabsDestination() = R.id.tabsFragment

    private fun getAuthDestination() {
        //todo: login destination id
    }
}