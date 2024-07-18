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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prafull.alarmy.alarms.domain.AlarmItem
import com.prafull.alarmy.alarms.ui.AlarmsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmItem(
    alarm: AlarmItem,
    viewModel: AlarmsViewModel,
    selectionMode: Boolean,
    selectedItems: MutableList<String>,
    onLongPress: () -> Unit
) {
    var enabled by remember { mutableStateOf(alarm.enabled) }
    val isSelected = selectedItems.contains(alarm.uid)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .combinedClickable(
                onClick = {
                    if (selectionMode) {
                        if (isSelected) selectedItems.remove(alarm.uid)
                        else selectedItems.add(alarm.uid)
                    }
                },
                onLongClick = {
                    onLongPress()
                    selectedItems.add(alarm.uid)
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
                Text(text = "${alarm.fullHour} : ${alarm.minutes} ${alarm.amPm.name}")
                Text(text = alarm.repeatMode.name)
            }
            if (selectionMode) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = {
                        if (it) selectedItems.add(alarm.uid)
                        else selectedItems.remove(alarm.uid)
                    },
                    modifier = Modifier.weight(.15f)
                )
            } else {
                Switch(
                    checked = enabled,
                    onCheckedChange = {
                        enabled = it
                        viewModel.toggleAlarm(alarm.uid, it)
                    },
                    modifier = Modifier.weight(.15f)
                )
            }
        }
    }
}