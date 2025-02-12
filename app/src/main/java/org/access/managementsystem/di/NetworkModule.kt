package org.access.managementsystem.di

import io.ktor.client.HttpClient
import org.access.managementsystem.network.ApiService
import org.access.managementsystem.network.httpClientAndroid
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