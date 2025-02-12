package org.access.managementsystem.di

import android.content.Context
import androidx.room.Room
import org.access.managementsystem.data.source.AmsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidContext()) }
}

fun provideDatabase(context: Context): AmsDatabase {
    return Room.databaseBuilder(
        context,
        AmsDatabase::class.java,
        AmsDatabase.DATABASE_NAME
    ).build()
}