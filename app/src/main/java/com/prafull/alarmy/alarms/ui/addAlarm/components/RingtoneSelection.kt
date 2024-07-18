package com.prafull.alarmy.alarms.ui.addAlarm.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafull.alarmy.Routes
import com.prafull.alarmy.alarms.ui.AlarmsViewModel

@Composable
internal fun RingtoneSelection(viewModel: AlarmsViewModel, navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Ringtone", fontWeight = FontWeight.W500, fontSize = 18.sp)
        Text(
            text = "Default Ringtone >",
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            modifier = Modifier.clickable {
                navController.navigate(Routes.SelectRingtoneScreen)
            })
    }
}