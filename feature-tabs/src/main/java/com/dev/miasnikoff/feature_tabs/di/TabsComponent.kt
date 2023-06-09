package com.dev.miasnikoff.feature_tabs.di

import com.dev.miasnikoff.core_di.annotations.FeatureScope
import com.dev.miasnikoff.core_navigation.di.FlowNavigationModule
import com.dev.miasnikoff.feature_tabs.di.module.*
import com.dev.miasnikoff.feature_tabs.ui.TabsFragment
import com.dev.miasnikoff.feature_tabs.ui.details.BookDetailsFragment
import com.dev.miasnikoff.feature_tabs.ui.home.HomeFragment
import com.dev.miasnikoff.feature_tabs.ui.list.BookListFragment
import com.dev.miasnikoff.feature_tabs.ui.list.FavoriteListFragment
import com.dev.miasnikoff.feature_tabs.ui.list.HistoryListFragment
import com.dev.miasnikoff.feature_tabs.ui.profile.EditFragment
import com.dev.miasnikoff.feature_tabs.ui.profile.ProfileFragment
import com.dev.miasnikoff.feature_tabs.ui.settings.SettingsFragment
import dagger.Component

@FeatureScope
@Component(
    modules = [
        TabsNetworkModule::class,
        TabDatabaseModule::class,
        TabsRepositoryModule::class,
        TabsInteractorModule::class,
        TabsViewModelModule::class,
        FlowNavigationModule::class
    ],
    dependencies = [TabsExternalDependencies::class]
)
interface TabsComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: TabsExternalDependencies): TabsComponent
    }

    fun inject(tabsFragment: TabsFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(bookListFragment: BookListFragment)
    fun inject(favoriteListFragment: FavoriteListFragment)
    fun inject(historyListFragment: HistoryListFragment)
    fun inject(detailsFragment: BookDetailsFragment)
    fun inject(detailsFragment: SettingsFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(editFragment: EditFragment)
}