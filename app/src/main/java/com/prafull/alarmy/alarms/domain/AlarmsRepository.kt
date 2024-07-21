package com.prafull.alarmy.alarms.domain

import kotlinx.coroutines.flow.Flow

interface AlarmsRepository {

    suspend fun insertAlarm(alarm: AlarmItem)
    fun getAlarms(): Flow<List<AlarmItem>>
    suspend fun deleteAlarm(alarm: AlarmItem)
    suspend fun toggleAlarm(boolean: Boolean, alarm: AlarmItem)
    suspend fun deleteAlarms(toList: List<AlarmItem>)
}