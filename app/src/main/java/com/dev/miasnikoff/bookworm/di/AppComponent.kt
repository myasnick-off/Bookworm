package com.dev.miasnikoff.bookworm.di

import android.content.Context
import com.dev.miasnikoff.bookworm.di.module.*
import com.dev.miasnikoff.bookworm.ui.details.VolumeDetailsFragment
import com.dev.miasnikoff.bookworm.ui.home.HomeFragment
import com.dev.miasnikoff.bookworm.ui.list.BookListFragment
import com.dev.miasnikoff.bookworm.ui.list.LocalListFragment
import com.dev.miasnikoff.bookworm.ui.login.LoginFragment
import com.dev.miasnikoff.bookworm.ui.main.MainActivity
import com.dev.miasnikoff.bookworm.ui.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        DBModule::class,
        RepositoryModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(bookListFragment: BookListFragment)
    fun inject(localListFragment: LocalListFragment)
    fun inject(detailsFragment: VolumeDetailsFragment)
    fun inject(loginFragment: LoginFragment)
}