package com.prafull.alarmy.alarms.data

import com.prafull.alarmy.alarms.data.db.AlarmsDao
import com.prafull.alarmy.alarms.domain.AlarmItem
import com.prafull.alarmy.alarms.domain.AlarmsRepository
import com.prafull.alarmy.alarms.mappers.toAlarmEntity
import com.prafull.alarmy.alarms.mappers.toAlarmItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlarmRepositoryImpl : AlarmsRepository, KoinComponent {
    private val alarmsDao by inject<AlarmsDao>()
    override suspend fun insertAlarm(alarm: AlarmItem) {
        alarmsDao.insertAlarm(alarm.toAlarmEntity())
    }

    override fun getAlarms(): Flow<List<AlarmItem>> {
        return alarmsDao.getAlarms().map { list ->
            list.map { it.toAlarmItem() }
        }
    }

    override suspend fun deleteAlarm(alarm: AlarmItem) {
        alarmsDao.deleteAlarm(alarm.toAlarmEntity())
    }

    override suspend fun toggleAlarm(alarmId: String, boolean: Boolean) {
        alarmsDao.toggleAlarm(alarmId, boolean)
    }
}