package com.prafull.alarmy.pomodoro

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PomodoroScreen(context: ComponentActivity, viewModel: PomodoroViewModel) {
    val timer by viewModel.timer.collectAsState()
    LaunchedEffect(timer) {
        Log.d("MainActivity", "Timer $timer")
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = timer.toString())
        Button(onClick = {
            viewModel.startService(context)
        }) {
            Text(text = "Start")
        }
        Button(onClick = {
            viewModel.stopService(context)
        }) {
            Text(text = "Stop")
        }
        Button(onClick = {
            viewModel.pauseService(context)
        }) {
            Text(text = "Pause")
        }
        Button(onClick = { viewModel.continueService(context) }) {
            Text(text = "Continue")
        }
        Button(onClick = {
            viewModel.resetService(context)
        }) {
            Text(text = "Reset")
        }
    }
}