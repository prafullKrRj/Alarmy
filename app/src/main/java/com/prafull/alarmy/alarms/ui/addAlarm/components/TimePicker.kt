package com.prafull.alarmy.alarms.ui.addAlarm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import com.prafull.alarmy.alarms.domain.AlarmItem
import com.prafull.alarmy.alarms.domain.AmPm
import com.prafull.alarmy.alarms.ui.AlarmsViewModel

@Composable
fun AlarmTimePicker(viewModel: AlarmsViewModel) {
    val onValueChange = { hours: AlarmItem ->
        viewModel.newAlarm = viewModel.newAlarm.copy(
            hours = hours.hours,
            minutes = hours.minutes,
            amPm = hours.amPm,
            fullHour = if (hours.amPm == AmPm.PM) 12 + hours.hours else hours.hours
        )
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        NumberPicker(
            modifier = Modifier.weight(.3f),
            value = viewModel.newAlarm.hours, range = 1..12,
            onValueChange = {
                onValueChange(
                    viewModel.newAlarm.copy(
                        hours = it,
                    ),
                )
            },
            dividersColor = Color.Transparent,
            textStyle = TextStyle(
                fontSize = 37.sp,
                fontWeight = FontWeight.SemiBold
            ),
        )
        VerticalDivider(thickness = 2.dp, color = Color.Gray, modifier = Modifier.height(50.dp))
        NumberPicker(
            modifier = Modifier.weight(.3f),
            value = viewModel.newAlarm.minutes, range = 0..59,
            onValueChange = {
                onValueChange(
                    viewModel.newAlarm.copy(
                        minutes = it
                    )
                )
            },
            dividersColor = Color.Transparent,
            textStyle = TextStyle(
                fontSize = 37.sp,
                fontWeight = FontWeight.SemiBold
            ),
        )
        VerticalDivider(thickness = 2.dp, color = Color.Gray, modifier = Modifier.height(50.dp))
        ListItemPicker(
            modifier = Modifier.weight(.3f),
            value = viewModel.newAlarm.amPm.name,
            onValueChange = {
                onValueChange(
                    viewModel.newAlarm.copy(
                        amPm = AmPm.valueOf(it)
                    )
                )
            },
            list = list,
            dividersColor = Color.Transparent,
            textStyle = TextStyle(
                fontSize = 37.sp,
                fontWeight = FontWeight.SemiBold
            ),
        )
    }

}

val list = listOf(AmPm.AM.name, AmPm.PM.name)