package com.prafull.alarmy.pomodoro


import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class PomodoroViewModel(application: Application) : AndroidViewModel(application), KoinComponent {

    @SuppressLint("StaticFieldLeak")
    private lateinit var service: PomodoroService
    var isRunning by mutableStateOf(false)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val pomodoroBinder = binder as PomodoroService.PomodoroBinder
            service = pomodoroBinder.service
            isRunning = true
            viewModelScope.launch {
                service.timer.collect {
                    _timer.value = it
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isRunning = false
        }
    }

    private val _timer = MutableStateFlow(0L)
    val timer: StateFlow<Long> get() = _timer.asStateFlow()

    private var setTime by mutableLongStateOf(1500L)

    init {
        val intent = Intent(application, PomodoroService::class.java)
        application.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onCleared() {
        super.onCleared()
        getApplication<Application>().unbindService(serviceConnection)
    }

    fun startService(mainActivity: ComponentActivity) {
        val intent = Intent(mainActivity, PomodoroService::class.java).apply {
            action = PomodoroService.Actions.START.name
            putExtra("time", setTime)
        }
        mainActivity.startForegroundService(intent)
    }

    fun stopService(mainActivity: ComponentActivity) {
        val intent = Intent(mainActivity, PomodoroService::class.java).apply {
            action = PomodoroService.Actions.STOP.name
        }
        mainActivity.startForegroundService(intent)
    }

    fun pauseService(mainActivity: ComponentActivity) {
        val intent = Intent(mainActivity, PomodoroService::class.java).apply {
            action = PomodoroService.Actions.PAUSE.name
        }
        mainActivity.startForegroundService(intent)
    }

    fun continueService(mainActivity: ComponentActivity) {
        val intent = Intent(mainActivity, PomodoroService::class.java).apply {
            action = PomodoroService.Actions.CONTINUE.name
        }
        mainActivity.startForegroundService(intent)
    }

    fun resetService(mainActivity: ComponentActivity) {
        val intent = Intent(mainActivity, PomodoroService::class.java).apply {
            action = PomodoroService.Actions.RESET.name
        }
        mainActivity.startForegroundService(intent)
    }
}
