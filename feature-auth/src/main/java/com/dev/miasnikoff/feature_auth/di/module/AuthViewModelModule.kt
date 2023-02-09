package com.dev.miasnikoff.feature_auth.di.module

import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.core_di.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(com.dev.miasnikoff.feature_auth.ui.LoginViewModel::class)
    fun bindLoginViewModel(viewModel: com.dev.miasnikoff.feature_auth.ui.LoginViewModel): ViewModel
}