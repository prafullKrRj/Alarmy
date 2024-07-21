package com.prafull.alarmy.alarms.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AlarmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alarmUid = intent.getStringExtra("ALARM_UID") ?: ""
        val message = intent.getStringExtra("ALARM_MESSAGE") ?: "Alarm!"

        setContent {
            MaterialTheme {
                AlarmScreen(
                    message = message,
                    onStop = { stopAlarm(alarmUid) },
                    onSnooze = { snoozeAlarm(alarmUid) }
                )
            }
        }
    }

    private fun stopAlarm(alarmUid: String) {
        // Implement logic to stop the alarm and update the database
        // You'll need to inject your database repository here
        // databaseRepository.updateAlarmStatus(alarmUid, false)
        finish()
    }

    private fun snoozeAlarm(alarmUid: String) {
        // Implement logic to snooze the alarm
        // You can reschedule the alarm for a few minutes later
        // AlarmScheduler(this).snoozeAlarm(alarmUid)
        finish()
    }
}

@Composable
fun AlarmScreen(
    message: String,
    onStop: () -> Unit,
    onSnooze: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Alarm",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = message,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onStop,
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("Stop")
                }
                Button(
                    onClick = onSnooze,
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("Snooze")
                }
            }
        }
    }
}