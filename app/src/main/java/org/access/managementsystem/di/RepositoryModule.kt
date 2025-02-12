package org.access.managementsystem.di

import org.access.managementsystem.data.repository.KtorRepositoryImpl
import org.access.managementsystem.data.repository.RoomRepository
import org.access.managementsystem.domain.repository.LocalRepository
import org.access.managementsystem.domain.repository.RemoteRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<LocalRepository> { RoomRepository(get()) }
    single<RemoteRepository> { KtorRepositoryImpl(get(), get()) }
}