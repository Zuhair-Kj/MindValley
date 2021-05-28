package com.adademy.discovery

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

import org.koin.core.context.stopKoin

class TestApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApp)
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}