package com.prafull.alarmy.pomodoro

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.prafull.alarmy.pomodoro.PomodoroService.Actions
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PomodoroNotificationReceiver: BroadcastReceiver(), KoinComponent {
    private val service: PomodoroService by inject()
    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {
            Actions.START.name -> {
                service.startTimer(intent.getLongExtra("time", 1500L))
            }
            Actions.STOP.name -> service.stopTimer()
            Actions.PAUSE.name -> service.pauseTimer()
            Actions.CONTINUE.name -> service.continueTimer()
            Actions.RESET.name -> service.resetTimer(intent.getLongExtra("time", 1500L))
        }
    }
}