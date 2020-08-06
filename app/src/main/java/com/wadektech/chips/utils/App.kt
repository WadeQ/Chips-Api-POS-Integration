package com.wadektech.chips.utils

import android.app.Application
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        app = this
    }

    companion object {
        var app: App? = null
            private set
    }
}
