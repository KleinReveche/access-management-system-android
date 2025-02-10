package org.access.managementsystempos

import android.app.Application
import org.access.managementsystempos.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AccessManagementSystemApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AccessManagementSystemApp)
            modules(appModule)
        }
    }
}