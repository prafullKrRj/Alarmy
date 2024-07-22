package com.prafull.alarmy.stopwatch

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.prafull.alarmy.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@Composable
fun StopwatchScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val stopwatchManager = remember { StopwatchManager(context) }
    val state by stopwatchManager.state.collectAsState()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> stopwatchManager.saveState()
                Lifecycle.Event.ON_RESUME -> stopwatchManager.loadState()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatTime(state.elapsedTime),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleButton(
                onClick = {
                    if (state.isRunning) stopwatchManager.pause()
                    else stopwatchManager.start()
                },
                icon = if (state.isRunning) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24,
                contentDescription = if (state.isRunning) "Pause" else "Play"
            )

            if (state.elapsedTime > 0) {
                Spacer(modifier = Modifier.width(16.dp))
                CircleButton(
                    onClick = { stopwatchManager.stop() },
                    icon = R.drawable.baseline_stop_24,
                    contentDescription = "Stop"
                )
            }
        }
    }
}

@Composable
fun CircleButton(onClick: () -> Unit, @DrawableRes icon: Int, contentDescription: String) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(64.dp)
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = contentDescription)
    }
}

class StopwatchManager(private val context: Context) {
    private val _state = MutableStateFlow(StopwatchState())
    val state: StateFlow<StopwatchState> = _state.asStateFlow()
    private var job: Job? = null

    fun start() {
        if (!state.value.isRunning) {
            _state.value = state.value.copy(
                isRunning = true,
                startTime = System.currentTimeMillis() - state.value.elapsedTime
            )
            startUpdatingElapsedTime()
        }
    }

    fun pause() {
        _state.value = state.value.copy(isRunning = false)
        job?.cancel()
    }

    fun stop() {
        _state.value = StopwatchState()
        job?.cancel()
    }

    private fun startUpdatingElapsedTime() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                delay(10)
                _state.value = state.value.copy(
                    elapsedTime = System.currentTimeMillis() - state.value.startTime
                )
            }
        }
    }

    fun saveState() {
        context.getSharedPreferences("Stopwatch", Context.MODE_PRIVATE).edit()
            .putLong("elapsedTime", state.value.elapsedTime)
            .apply()
    }

    fun loadState() {
        val savedTime = context.getSharedPreferences("Stopwatch", Context.MODE_PRIVATE)
            .getLong("elapsedTime", 0)
        _state.value = state.value.copy(elapsedTime = savedTime)
    }
}

data class StopwatchState(
    val elapsedTime: Long = 0L,
    val isRunning: Boolean = false,
    val startTime: Long = 0L
)

fun formatTime(timeInMillis: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60
    val centiseconds = timeInMillis % 1000 / 10
    return String.format("%02d:%02d:%02d.%02d", hours, minutes, seconds, centiseconds)
}