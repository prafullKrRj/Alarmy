package com.prafull.alarmy

import android.app.Application
import org.koin.core.context.startKoin

class AlarmyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {

        }
    }
}