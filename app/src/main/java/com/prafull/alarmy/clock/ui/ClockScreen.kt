package com.prafull.alarmy.clock.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.prafull.alarmy.Routes
import com.prafull.alarmy.clock.ui.components.ClockText
import com.prafull.alarmy.commons.AddAndDeleteBottomBar

@Composable
fun ClockScreen(navController: NavController, viewModel: ClockViewModel) {
    var selectionMode by remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<String>() }
    Scaffold(
        bottomBar = {
            AddAndDeleteBottomBar(selectionMode = selectionMode, addClicked = {
                navController.navigate(Routes.AddClockScreen)
            }) {
                selectionMode = false
                viewModel.deleteCities(selectedItems.toList())
                selectedItems.clear()
            }
        }
    ) { paddingValues ->
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item("Time") {
                ClockText()
            }
        }
    }
}
