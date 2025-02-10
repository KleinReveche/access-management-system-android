package org.access.managementsystempos.di

import org.access.managementsystempos.features.login.LoginScreenViewModel
import org.access.managementsystempos.features.main.MainScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { MainScreenViewModel(get(), get()) }
}