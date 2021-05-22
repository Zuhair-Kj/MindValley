package com.adademy.mindvalley

import android.app.Application
import com.adademy.mindvalley.injection.networkModule
import org.koin.android.ext.koin.androidContext

class MindvalleyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@MindvalleyApplication)
            modules(
                listOf(networkModule)
            )

        }
    }
}