package com.prafull.alarmy.alarms.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.alarmy.alarms.domain.AlarmItem
import com.prafull.alarmy.alarms.domain.AlarmsRepository
import com.prafull.alarmy.alarms.domain.RepeatMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDateTime

class AlarmsViewModel : ViewModel(), KoinComponent {
    private val repository by inject<AlarmsRepository>()
    private val _alarms = repository.getAlarms()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val alarms = _alarms

    fun toggleAlarm(alarmId: String, boolean: Boolean) {
        viewModelScope.launch {
            repository.toggleAlarm(alarmId, boolean)
        }
    }

    fun addAlarm() {
        viewModelScope.launch {
            repository.insertAlarm(
                AlarmItem(
                    time = LocalDateTime.now(),
                    enabled = true,
                    repeatMode = RepeatMode.ONCE
                )
            )
        }
    }
}