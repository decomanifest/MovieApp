package com.example.movieapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
    }

    private fun initializeTimber() {
        clearLogs()
        Timber.plant(Timber.DebugTree())
    }

    private fun clearLogs() {
        runCatching { Runtime.getRuntime().exec("logcat --clear") }
    }
}