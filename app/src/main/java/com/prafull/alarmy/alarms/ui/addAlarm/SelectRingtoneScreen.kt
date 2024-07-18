package com.prafull.alarmy.alarms.ui.addAlarm

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.alarmy.alarms.ui.AlarmsViewModel
import com.prafull.alarmy.goBackStack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRingtone(viewModel: AlarmsViewModel, navController: NavController) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = "Select Ringtone")
        }, navigationIcon = {
            IconButton(onClick = {
                navController.goBackStack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        })
    }) { paddingValues ->
        LazyVerticalGrid(columns = GridCells.Adaptive(200.dp), contentPadding = paddingValues) {
            items(10) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(250.dp)
                        .padding(8.dp)
                ) {
                    Text(text = "Ringtone $it")
                }
            }
        }
    }
}