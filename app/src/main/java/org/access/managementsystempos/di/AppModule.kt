package org.access.managementsystempos.di

import org.koin.dsl.module

val appModule = module {
    includes(networkModule)
    includes(databaseModule)
    includes(repositoryModule)
    includes(viewModelModule)
}