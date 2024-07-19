package com.prafull.alarmy.clock.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun ClockText(modifier: Modifier = Modifier) {
    var currentTime by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf("") }
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss")
    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy a")

    LaunchedEffect(Unit) {
        while (true) {
            with(LocalDateTime.now()) {
                currentTime = format(timeFormatter)
                currentDate = format(dateFormatter)
            }
            kotlinx.coroutines.delay(1000L)
        }
    }

    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = currentTime,
            style = MaterialTheme.typography.displayMedium,
        )
        Text(
            text = currentDate
        )
    }
}