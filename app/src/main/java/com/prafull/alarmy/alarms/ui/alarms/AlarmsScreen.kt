package com.prafull.alarmy.alarms.ui.alarms

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.alarmy.Routes
import com.prafull.alarmy.alarms.ui.AlarmsViewModel
import com.prafull.alarmy.alarms.ui.alarms.components.AlarmItem
import com.prafull.alarmy.commons.AddAndDeleteBottomBar

@Composable
fun AlarmsScreen(viewModel: AlarmsViewModel, navController: NavController) {
    val alarms by viewModel.alarms.collectAsState()
    var selectionMode by remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<String>() }
    Scaffold(
        Modifier.fillMaxSize(),
        bottomBar = {
            AddAndDeleteBottomBar(selectionMode = selectionMode, addClicked = {
                navController.navigate(Routes.AddAlarmScreen)
            }) {
                selectionMode = false
                viewModel.deleteAlarms(selectedItems.toList())
                selectedItems.clear()
            }
        }
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

@Composable
fun TopIcon(
    @DrawableRes icon: Int,
    selected: Boolean,
) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = null,
        tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceDim,
        modifier = Modifier.size(32.dp)
    )
}