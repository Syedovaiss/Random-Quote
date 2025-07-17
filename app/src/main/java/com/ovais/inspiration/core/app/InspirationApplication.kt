package com.ovais.inspiration.core.app

import android.app.Application
import com.ovais.inspiration.core.di.factoryModule
import com.ovais.inspiration.core.di.networkModule
import com.ovais.inspiration.core.di.singletonModule
import com.ovais.inspiration.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class InspirationApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@InspirationApplication)
            modules(singletonModule, networkModule, factoryModule, viewModelModule)
        }
    }
}