package com.prafull.alarmy.alarms.ui.addAlarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafull.alarmy.alarms.domain.RepeatMode
import com.prafull.alarmy.alarms.ui.AlarmsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RepeatSelection(viewModel: AlarmsViewModel) {
    val scope = rememberCoroutineScope()
    val items = listOf(
        RepeatMode.ONCE.name,
        RepeatMode.DAILY.name,
        RepeatMode.MON_FRI.name,
        RepeatMode.CUSTOM.name
    )

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = {
            showBottomSheet = false
            scope.launch { sheetState.hide() }
        }, sheetState = sheetState) {
            Column(Modifier) {
                items.forEach {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (it == viewModel.newAlarm.repeatMode.name) MaterialTheme.colorScheme.surfaceDim else Color.Transparent,
                            )
                            .padding(16.dp)
                            .clickable {
                                viewModel.newAlarm =
                                    viewModel.newAlarm.copy(repeatMode = RepeatMode.valueOf(it))
                                scope.launch {
                                    showBottomSheet = false
                                    sheetState.hide()
                                }
                            }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = it, fontWeight = FontWeight.W500, fontSize = 16.sp)
                    }
                }
            }
        }
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Repeat", fontWeight = FontWeight.W500, fontSize = 18.sp)
        Text(
            text = viewModel.newAlarm.repeatMode.name + " >",
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            modifier = Modifier.clickable {
                scope.launch {
                    showBottomSheet = true
                    sheetState.expand()
                }
            }
        )
    }
}