package com.prafull.alarmy.alarms.ui.alarms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.prafull.alarmy.Routes
import com.prafull.alarmy.alarms.ui.AlarmsViewModel
import com.prafull.alarmy.alarms.ui.alarms.components.AlarmItem

@Composable
fun AlarmsScreen(viewModel: AlarmsViewModel, navController: NavController) {
    val alarms by viewModel.alarms.collectAsState()
    var selectionMode by remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<String>() }

    Scaffold(
        Modifier.fillMaxSize(),
        floatingActionButton = {
            if (selectionMode) {
                FloatingActionButton(onClick = {
                    viewModel.deleteAlarms(selectedItems.toList())
                    selectionMode = false
                    selectedItems.clear()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Alarms Button"
                    )
                }
            } else {
                FloatingActionButton(onClick = {
                    navController.navigate(Routes.AddAlarmScreen)
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Alarm Button")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize()) {
            items(alarms, key = { it.uid }) { alarm ->
                AlarmItem(
                    alarm = alarm,
                    viewModel = viewModel,
                    selectionMode = selectionMode,
                    selectedItems = selectedItems,
                    onLongPress = { selectionMode = true }
                )
            }
            if (alarms.isEmpty()) {
                item {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "No alarms set")
                    }
                }
            }
        }
    }
}