package org.access.managementsystempos.di

import io.ktor.client.HttpClient
import org.access.managementsystempos.network.ApiService
import org.access.managementsystempos.network.httpClientAndroid
import org.koin.dsl.module

val networkModule = module {
    single { provideHttpClient() }
    single { provideApiService(get()) }
}

fun provideHttpClient(): HttpClient {
    return httpClientAndroid
}

fun provideApiService(httpClient: HttpClient): ApiService {
    return ApiService(httpClient)
}