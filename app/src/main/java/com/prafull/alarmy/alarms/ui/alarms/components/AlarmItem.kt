package com.prafull.alarmy.alarms.ui.alarms.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafull.alarmy.alarms.domain.AlarmItem
import com.prafull.alarmy.alarms.ui.AlarmsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmItem(
    alarm: AlarmItem,
    viewModel: AlarmsViewModel,
    selectionMode: Boolean,
    onLongPress: () -> Unit,
    isSelected: Boolean,
    onItemClick: (Boolean) -> Unit
) {
    var enabled by remember { mutableStateOf(alarm.enabled) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .combinedClickable(
                onClick = {
                    if (selectionMode) {
                        if (isSelected) onItemClick(false)
                        else onItemClick(true)
                    }
                },
                onLongClick = {
                    onLongPress()
                    onItemClick(true)
                }
            )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(.85f)) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(MaterialTheme.typography.headlineSmall.toSpanStyle()) {
                            append(getAlarmTime(alarm.hours, alarm.minutes))
                        }
                        withStyle(MaterialTheme.typography.bodySmall.toSpanStyle()) {
                            append(" ${alarm.amPm}")
                        }
                    },
                    fontSize = 20.sp,
                    color = Color.Black.copy(alpha = if (alarm.enabled) 1f else 0.4f)
                )
                Text(
                    text = alarm.repeatMode.name,
                    fontSize = 14.sp,
                    color = Color.Black.copy(alpha = if (alarm.enabled) 1f else 0.4f)
                )
            }
            if (selectionMode) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = {
                        if (it) onItemClick(true)
                        else onItemClick(false)
                    },
                    modifier = Modifier.weight(.15f)
                )
            } else {
                Switch(
                    checked = enabled,
                    onCheckedChange = {
                        enabled = it
                        viewModel.toggleAlarm(alarm, it)
                    },
                    modifier = Modifier.weight(.15f)
                )
            }
        }
    }
}

private fun getAlarmTime(hours: Int, minutes: Int): String {
    val hour = if (hours < 10) "0$hours" else hours.toString()
    val minute = if (minutes < 10) "0$minutes" else minutes.toString()
    return "$hour:$minute"
}