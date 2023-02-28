package com.dev.miasnikoff.bookworm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dev.miasnikoff.core_di.FeatureExternalDependenciesContainer
import com.dev.miasnikoff.core_di.FeatureExternalDepsProvider
import com.dev.miasnikoff.core_di.annotations.GlobalNavHolder
import com.dev.miasnikoff.core_navigation.navigator.NavigatorHolder
import com.dev.miasnikoff.core_navigation.router.GlobalRouter
import com.dev.miasnikoff.feature_auth_api.AuthFeatureApi
import com.dev.miasnikoff.feature_tabs_api.TabsFeatureApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    lateinit var globalRouter: GlobalRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            setNavigationGraph(getSplashGraphId())
            lifecycleScope.launch {
                delay(SPLASH_SCREEN_SHOW_TIME)
                setNavigationGraph(getMainGraphId())
            }
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
        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        return navHost.navController
    }

    private fun setNavigationGraph(graphResId: Int) {
        globalRouter.setNewGraph(graphResId)
    }

    private fun getSplashGraphId() = com.dev.miasnikoff.splash.R.navigation.splash_nav_graph

    private fun getMainGraphId() = R.navigation.main_nav_graph

    companion object {
        private const val SPLASH_SCREEN_SHOW_TIME = 2000L
    }
}