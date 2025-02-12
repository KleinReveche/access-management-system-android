package org.access.managementsystem.di

import org.access.managementsystem.MainActivityViewModel
import org.access.managementsystem.features.login.LoginScreenViewModel
import org.access.managementsystem.features.main.MainScreenViewModel
import org.access.managementsystem.features.pos.POSScreenViewModel
import org.access.managementsystem.features.pos.SharedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { MainScreenViewModel(get(), get()) }
    viewModel { SharedViewModel() }
    viewModel { MainActivityViewModel(get(), get()) }
    viewModel { POSScreenViewModel(get()) }
}