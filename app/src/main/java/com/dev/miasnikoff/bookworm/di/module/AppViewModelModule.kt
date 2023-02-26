package com.dev.miasnikoff.bookworm.di.module

import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.bookworm.main.MainViewModel
import com.dev.miasnikoff.core_di.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AppViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindHomeViewModel(viewModel: MainViewModel): ViewModel
}