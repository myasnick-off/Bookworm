package com.dev.miasnikoff.bookworm.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.miasnikoff.bookworm.di.ViewModelKey
import com.dev.miasnikoff.bookworm.ui._core.ViewModelFactory
import com.dev.miasnikoff.bookworm.ui.home.HomeViewModel
import com.dev.miasnikoff.bookworm.ui.list.BookListViewModel
import com.dev.miasnikoff.bookworm.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Module
interface ViewModelModule {

    @Singleton
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookListViewModel::class)
    fun bindBookListViewModel(viewModel: BookListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}