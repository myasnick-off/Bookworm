package com.dev.miasnikoff.bookworm.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dev.miasnikoff.bookworm.App
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ActivityMainBinding
import com.dev.miasnikoff.bookworm.di.GlobalNavHolder
import com.dev.miasnikoff.bookworm.utils.navigation.navholder.NavigatorHolder
import com.dev.miasnikoff.bookworm.utils.navigation.router.GlobalRouter
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    @GlobalNavHolder
    lateinit var navigatorHolder: NavigatorHolder<NavController>

    @Inject
    lateinit var globalRouter: GlobalRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appInstance.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)

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

    private fun getLoginDestination() {
        //todo: login destination id
    }
}