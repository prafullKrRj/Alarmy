package com.prafull.alarmy.alarms.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.alarmy.alarms.domain.AlarmItem
import com.prafull.alarmy.alarms.domain.AlarmsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlarmsViewModel(
    private val alarmId: String = ""
) : ViewModel(), KoinComponent {
    private val repository by inject<AlarmsRepository>()
    private val _alarms = repository.getAlarms()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val alarms = _alarms

    var newAlarm by mutableStateOf(AlarmItem())
    private fun resetAlarm() {
        newAlarm = AlarmItem()
    }

    fun toggleAlarm(alarmId: String, boolean: Boolean) {
        viewModelScope.launch {
            repository.toggleAlarm(alarmId, boolean)
        }
    }

    fun addAlarm() {
        viewModelScope.launch {
            repository.insertAlarm(
                newAlarm
            )
            resetAlarm()
        }
    }

    fun deleteAlarms(toList: List<String>) {
        viewModelScope.launch {
            repository.deleteAlarms(toList)
        }
    }
}