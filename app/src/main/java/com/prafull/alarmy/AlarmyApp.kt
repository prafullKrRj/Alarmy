package com.prafull.alarmy

import android.app.Application
import com.prafull.alarmy.alarms.di.alarmModule
import com.prafull.alarmy.clock.domain.clockModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AlarmyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AlarmyApp)
            modules(alarmModule, clockModule)
        }
    }
}