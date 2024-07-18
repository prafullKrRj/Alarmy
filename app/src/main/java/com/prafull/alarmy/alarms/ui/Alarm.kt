package com.prafull.alarmy.alarms.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafull.alarmy.Routes
import com.prafull.alarmy.alarms.ui.addAlarm.AddAlarmScreen
import com.prafull.alarmy.alarms.ui.addAlarm.SelectRingtone
import com.prafull.alarmy.alarms.ui.alarms.AlarmsScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun Alarms() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.AlarmScreen
    ) {
        composable<Routes.AlarmScreen> {
            AlarmsScreen(getViewModel(), navController)
        }
        composable<Routes.AddAlarmScreen> {
            AddAlarmScreen(getViewModel(), navController)
        }
        composable<Routes.SelectRingtoneScreen> {
            SelectRingtone(
                viewModel = getViewModel(),
                navController = navController
            )
        }
    }
}