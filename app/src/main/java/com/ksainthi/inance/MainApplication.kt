package com.ksainthi.inance

import android.app.Application
import com.ksainthi.inance.di.networkModule
import com.ksainthi.inance.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@MainApplication)
            androidLogger()
            modules(networkModule)
            modules(presentationModule)
        }

    }
}