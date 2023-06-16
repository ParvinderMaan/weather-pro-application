package com.jpmorgan.weather

import android.app.Application
import timber.log.Timber

class WeatherProApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() = Timber.plant(Timber.DebugTree())
}