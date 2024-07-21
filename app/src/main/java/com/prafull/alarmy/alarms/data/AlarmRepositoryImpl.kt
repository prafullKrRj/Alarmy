package com.prafull.alarmy.alarms.data

import com.prafull.alarmy.alarms.data.db.AlarmsDao
import com.prafull.alarmy.alarms.domain.AlarmItem
import com.prafull.alarmy.alarms.domain.AlarmsRepository
import com.prafull.alarmy.alarms.domain.mappers.toAlarmEntity
import com.prafull.alarmy.alarms.domain.mappers.toAlarmItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlarmRepositoryImpl : AlarmsRepository, KoinComponent {
    private val alarmsDao by inject<AlarmsDao>()
    private val alarmScheduler by inject<AlarmScheduler>()

    override suspend fun insertAlarm(alarm: AlarmItem) {
        alarmScheduler.scheduleAlarm(alarm)
        alarmsDao.insertAlarmIfNotExists(alarm.toAlarmEntity())
    }

    override fun getAlarms(): Flow<List<AlarmItem>> {
        return alarmsDao.getAlarms().map { list ->
            list.map { it.toAlarmItem() }
        }
    }

    override suspend fun deleteAlarm(alarm: AlarmItem) {
        alarmScheduler.cancelAlarm(alarm)
        alarmsDao.deleteAlarm(alarm.toAlarmEntity())
    }

    override suspend fun toggleAlarm(boolean: Boolean, alarm: AlarmItem) {
        if (boolean) {
            alarmScheduler.scheduleAlarm(alarm)
        } else {
            alarmScheduler.cancelAlarm(alarm)
        }
        alarmsDao.toggleAlarm(alarm.uid, boolean)
    }

    override suspend fun deleteAlarms(toList: List<AlarmItem>) {
        alarmsDao.deleteAlarms(toList.map { it.uid })
        coroutineScope {
            toList.forEach { alarmScheduler.cancelAlarm(it) }
        }
    }
}