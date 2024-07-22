package com.prafull.alarmy.pomodoro

import android.app.NotificationManager
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pomodoroModule = module {
    viewModel { PomodoroViewModel(get()) }
    single { PomodoroService() }
    single<NotificationManager> {
        androidContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}