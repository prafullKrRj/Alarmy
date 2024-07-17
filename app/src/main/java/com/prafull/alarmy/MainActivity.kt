package com.prafull.alarmy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafull.alarmy.alarms.ui.AddAlarmScreen
import com.prafull.alarmy.alarms.ui.AlarmsScreen
import com.prafull.alarmy.ui.theme.AlarmyTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlarmyTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.AlarmScreen) {
                    composable<Routes.AlarmScreen> {
                        AlarmsScreen(getViewModel(), navController)
                    }
                    composable<Routes.AddAlarmScreen> {
                        AddAlarmScreen(getViewModel(), navController)
                    }
                }
            }
        }
    }
}


fun NavController.goBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}