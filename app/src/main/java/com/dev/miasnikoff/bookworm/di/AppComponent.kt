package com.dev.miasnikoff.bookworm.di

import android.content.Context
import com.dev.miasnikoff.bookworm.di.module.*
import com.dev.miasnikoff.bookworm.ui.details.BookDetailsFragment
import com.dev.miasnikoff.bookworm.ui.home.HomeFragment
import com.dev.miasnikoff.bookworm.ui.list.BookListFragment
import com.dev.miasnikoff.bookworm.ui.list.LocalListFragment
import com.dev.miasnikoff.bookworm.ui.login.LoginFragment
import com.dev.miasnikoff.bookworm.ui.main.MainActivity
import com.dev.miasnikoff.bookworm.ui.main.TabsFragment
import com.dev.miasnikoff.bookworm.ui.profile.EditFragment
import com.dev.miasnikoff.bookworm.ui.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkProvideModule::class,
        NetworkBindsModule::class,
        DBModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        NavigationModule::class,
        NavigationBindModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(tabsFragment: TabsFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(bookListFragment: BookListFragment)
    fun inject(localListFragment: LocalListFragment)
    fun inject(detailsFragment: BookDetailsFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(editFragment: EditFragment)
}