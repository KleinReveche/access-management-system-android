package org.access.managementsystempos.di

import org.access.managementsystempos.data.repository.KtorRepositoryImpl
import org.access.managementsystempos.data.repository.RoomRepository
import org.access.managementsystempos.domain.repository.LocalRepository
import org.access.managementsystempos.domain.repository.RemoteRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<LocalRepository> { RoomRepository(get()) }
    single<RemoteRepository> { KtorRepositoryImpl(get(), get()) }
}