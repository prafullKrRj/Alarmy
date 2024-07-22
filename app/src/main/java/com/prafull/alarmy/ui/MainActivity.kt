package com.prafull.alarmy.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafull.alarmy.R.drawable.baseline_access_alarms_24
import com.prafull.alarmy.R.drawable.baseline_access_time_filled_24
import com.prafull.alarmy.R.drawable.baseline_hourglass_empty_24
import com.prafull.alarmy.R.drawable.baseline_hourglass_full_24
import com.prafull.alarmy.R.drawable.baseline_timer_24
import com.prafull.alarmy.R.drawable.outline_access_time_24
import com.prafull.alarmy.R.drawable.outline_timer_24
import com.prafull.alarmy.R.drawable.twotone_access_alarm_24
import com.prafull.alarmy.alarms.ui.addAlarm.AddAlarmScreen
import com.prafull.alarmy.alarms.ui.addAlarm.SelectRingtone
import com.prafull.alarmy.alarms.ui.alarms.AlarmsScreen
import com.prafull.alarmy.alarms.ui.alarms.TopIcon
import com.prafull.alarmy.clock.ui.AddCityScreen
import com.prafull.alarmy.clock.ui.ClockScreen
import com.prafull.alarmy.pomodoro.PomodoroScreen
import com.prafull.alarmy.stopwatch.StopwatchScreen
import com.prafull.alarmy.ui.theme.AlarmyTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlarmyTheme {
                val navController = rememberNavController()
                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = Routes.AlarmScreen
                ) {
                    composable<Routes.AlarmScreen> {
                        MainApp(navController = navController, this@MainActivity)
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
                    composable<Routes.AddCityScreen> {
                        AddCityScreen(navController, getViewModel())
                    }
                }
            }
        }
    }
}

@Composable
fun MainApp(navController: NavController, activity: ComponentActivity) {
    val pagerState = rememberPagerState(
        pageCount = { 4 },
        initialPage = 2
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(currentPage = pagerState.currentPage)
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues
        ) { page ->
            when (page) {
                0 -> {
                    AlarmsScreen(getViewModel(), navController)
                }

                1 -> {
                    ClockScreen(navController, getViewModel())
                }

                2 -> {
                    PomodoroScreen(activity, getViewModel())
                }

                3 -> {
                    StopwatchScreen()
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(currentPage: Int) {
    CenterAlignedTopAppBar(title = {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TopIcon(
                icon = if (currentPage == 0) twotone_access_alarm_24 else baseline_access_alarms_24,
                selected = currentPage == 0
            )
            TopIcon(
                icon = if (currentPage == 1) baseline_access_time_filled_24 else outline_access_time_24,
                selected = currentPage == 1
            )
            TopIcon(
                icon = if (currentPage == 2) baseline_hourglass_full_24 else baseline_hourglass_empty_24,
                selected = currentPage == 2
            )
            TopIcon(
                icon = if (currentPage == 3) baseline_timer_24 else outline_timer_24,
                selected = currentPage == 3
            )
        }
    })
}

fun NavController.goBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}
