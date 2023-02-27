package com.dev.miasnikoff.feature_tabs.di.module

import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.core_di.annotations.ViewModelKey
import com.dev.miasnikoff.feature_tabs.ui.home.HomeViewModel
import com.dev.miasnikoff.feature_tabs.ui.profile.EditViewModel
import com.dev.miasnikoff.feature_tabs.ui.profile.ProfileViewModel
import com.dev.miasnikoff.feature_tabs.ui.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface TabsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditViewModel::class)
    fun bindEditViewModel(viewModel: EditViewModel): ViewModel
}