package com.dev.miasnikoff.bookworm.di

import android.content.Context
import com.dev.miasnikoff.bookworm.di.module.*
import com.dev.miasnikoff.bookworm.ui.details.BookDetailsFragment
import com.dev.miasnikoff.bookworm.ui.home.HomeFragment
import com.dev.miasnikoff.bookworm.ui.list.BookListFragment
import com.dev.miasnikoff.bookworm.ui.list.LocalListFragment
import com.dev.miasnikoff.bookworm.ui.login.LoginFragment
import com.dev.miasnikoff.bookworm.ui.profile.EditFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkProvideModule::class,
        NetworkBindsModule::class,
        DBModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(homeFragment: HomeFragment)
    fun inject(bookListFragment: BookListFragment)
    fun inject(localListFragment: LocalListFragment)
    fun inject(detailsFragment: BookDetailsFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(editFragment: EditFragment)
}