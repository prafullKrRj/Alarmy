package com.prafull.alarmy

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.prafull.alarmy.alarms.di.alarmModule
import com.prafull.alarmy.clock.domain.clockModule
import com.prafull.alarmy.pomodoro.PomodoroService.Companion.NOTIFICATION_CHANNEL_ID
import com.prafull.alarmy.pomodoro.pomodoroModule
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
            modules(alarmModule, clockModule, pomodoroModule)
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Pomodoro Channel",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {

        }
        channel.description = "Pomodoro Timer Notifications"
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }
}