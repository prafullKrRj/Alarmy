package com.prafull.alarmy.alarms.ui.addAlarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafull.alarmy.alarms.ui.AlarmsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AlarmLabel(viewModel: AlarmsViewModel) {
    var openBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    if (openBottomSheet) {
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        val value = remember { mutableStateOf(viewModel.newAlarm.message) }
        ModalBottomSheet(onDismissRequest = {
            scope.launch { sheetState.hide() }
            openBottomSheet = false
        }, sheetState = sheetState) {
            Column(
                Modifier
                    .padding(16.dp)
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    IconButton(onClick = {
                        openBottomSheet = false
                        scope.launch { sheetState.hide() }
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                    Text(
                        text = "Enter Message",
                        fontWeight = FontWeight.W500,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    IconButton(onClick = {
                        viewModel.newAlarm = viewModel.newAlarm.copy(message = value.value)
                        openBottomSheet = false
                        scope.launch { sheetState.hide() }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Check")
                    }
                }
                OutlinedTextField(
                    value = value.value,
                    onValueChange = {
                        value.value = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .focusRequester(focusRequester),
                    textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W400),
                    shape = MaterialTheme.shapes.medium,
                )
            }
        }
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.surfaceDim)
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Label", fontWeight = FontWeight.W500, fontSize = 18.sp)
        Text(
            text = viewModel.newAlarm.message.ifEmpty { "Enter Message" },
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            modifier = Modifier.clickable {
                scope.launch {
                    openBottomSheet = true
                    sheetState.expand()
                }
            }
        )
    }
}