package com.prafull.alarmy.alarms.ui.addAlarm

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.prafull.alarmy.alarms.ui.AlarmsViewModel
import com.prafull.alarmy.alarms.ui.addAlarm.components.AlarmTimePicker
import com.prafull.alarmy.goBackStack


@Composable
fun AddAlarmScreen(viewModel: AlarmsViewModel, navController: NavController) {

    Scaffold(topBar = {
        AddAlarmTopAppBar(navController = navController, viewModel = viewModel)
    }) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            item {
                AlarmTimePicker(viewModel = viewModel)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddAlarmTopAppBar(navController: NavController, viewModel: AlarmsViewModel) {
    CenterAlignedTopAppBar(title = {
        Text(text = "Add Alarm")
    }, navigationIcon = {
        IconButton(onClick = {
            navController.goBackStack()
        }) {
            Icon(Icons.Default.Clear, contentDescription = "Clear")
        }
    }, actions = {
        IconButton(onClick = {
            viewModel.addAlarm()
            navController.goBackStack()
        }) {
            Icon(Icons.Default.Check, contentDescription = "Check")
        }
    })
}