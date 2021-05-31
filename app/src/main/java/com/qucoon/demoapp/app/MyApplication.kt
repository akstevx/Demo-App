package com.qucoon.demoapp.app

import android.app.Application
import com.qucoon.demoapp.BuildConfig
import com.qucoon.demoapp.modules.networkModule
import com.qucoon.demoapp.modules.repositoryModule
import com.qucoon.demoapp.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger(Level.DEBUG)
            modules(listOf(repositoryModule, viewModelModule, networkModule))
        }
    }

    private fun initTimber(){
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}