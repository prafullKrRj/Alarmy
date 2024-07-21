package com.prafull.alarmy.pomodoro

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import com.prafull.alarmy.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class PomodoroService : Service(), KoinComponent {
    private val context: Context by inject()
    private var timerJob: Job? = null
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val notificationManager by inject<NotificationManager>()
    private val _timer = MutableStateFlow(0L)
    val timer = _timer.asStateFlow()
    private var setTime by mutableLongStateOf(0L)

    inner class PomodoroBinder : Binder() {
        val service: PomodoroService
            get() = this@PomodoroService
    }

    private val binder = PomodoroBinder()

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "pomodoro_channel"
        const val NOTIFICATION_ID = 1
    }

    override fun onBind(intent: Intent?): IBinder = binder


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotifications()

        when (intent?.action) {
            Actions.START.name -> {
                val time = intent.getLongExtra("time", 1500L)
                startTimer(time)
                setTime = time
            }

            Actions.STOP.name -> stopTimer()
            Actions.PAUSE.name -> pauseTimer()
            Actions.CONTINUE.name -> continueTimer()
            Actions.RESET.name -> resetTimer(setTime)
        }

        return START_STICKY
    }

    private fun showNotifications() {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Pomodoro Timer Running")
            .setContentText("${timer.value / 60}:${timer.value % 60}")
            .setSmallIcon(R.drawable.outline_timer_24) // Ensure you have a drawable resource for the icon
            .addAction(
                R.drawable.outline_timer_24,
                "Pause",
                PendingIntent.getService(
                    this,
                    0,
                    Intent(this, PomodoroNotificationReceiver::class.java).apply {
                        action = Actions.PAUSE.name
                    },
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    fun startTimer(time: Long) {
        timerJob?.cancel()
        _timer.value = time // Initialize timer with the starting time
        timerJob = serviceScope.launch {
            while (_timer.value > 0) {
                delay(1000) // Wait for a second
                _timer.value -= 1 // Decrement the timer
                showNotifications()
            }
        }
    }

    fun continueTimer() {
        timerJob?.cancel()
        timerJob = serviceScope.launch {
            while (_timer.value > 0) {
                delay(1000) // Wait for a second
                _timer.value -= 1 // Decrement the timer
                showNotifications()
            }
        }
    }

    fun resetTimer(time: Long) {
        timerJob?.cancel()
        _timer.value = time
        showNotifications()
    }

    fun pauseTimer() {
        timerJob?.cancel()
    }

    fun stopTimer() {
        timerJob?.cancel()
        _timer.value = setTime
        setTime = 0L
        notificationManager.cancel(NOTIFICATION_ID)
        stopSelf()
    }

    enum class Actions {
        START, STOP, PAUSE, CONTINUE, RESET
    }

    override fun onDestroy() {
        super.onDestroy()
        timerJob?.cancel()
    }
}


