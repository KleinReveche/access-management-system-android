package org.access.managementsystempos.di

import org.access.managementsystempos.MainActivityViewModel
import org.access.managementsystempos.features.login.LoginScreenViewModel
import org.access.managementsystempos.features.main.MainScreenViewModel
import org.access.managementsystempos.features.pos.POSScreenViewModel
import org.access.managementsystempos.features.pos.SharedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { MainScreenViewModel(get(), get()) }
    viewModel { SharedViewModel() }
    viewModel { MainActivityViewModel(get(), get()) }
    viewModel { POSScreenViewModel(get()) }
}