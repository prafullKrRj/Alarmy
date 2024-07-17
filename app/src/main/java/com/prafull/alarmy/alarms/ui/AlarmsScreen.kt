package com.prafull.alarmy.alarms.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.alarmy.Routes
import com.prafull.alarmy.alarms.domain.AlarmItem

@Composable
fun AlarmsScreen(viewModel: AlarmsViewModel, navController: NavController) {
    val alarms by viewModel.alarms.collectAsState()
    Scaffold(
        Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.AddAlarmScreen)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Alarm Button")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize()) {
            items(alarms, key = {
                it.uid
            }) { alarm ->
                AlarmItem(alarm, viewModel)
            }
            if (alarms.isEmpty()) {
                item {
                    Text(text = "No alarms set")
                }
            }
        }
    }
}

@Composable
fun AlarmItem(alarmItem: AlarmItem, viewModel: AlarmsViewModel) {
    var enabled by remember {
        mutableStateOf(alarmItem.enabled)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(.85f)) {
                Text(text = alarmItem.time.toString())
                Text(text = alarmItem.repeatMode.name)
            }
            Switch(modifier = Modifier.weight(.15f), checked = enabled, onCheckedChange = {
                enabled = it
                viewModel.toggleAlarm(alarmItem.uid, it)
            })
        }
    }
}