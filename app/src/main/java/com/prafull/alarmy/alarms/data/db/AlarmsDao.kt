package com.prafull.alarmy.alarms.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface AlarmsDao {
    @Upsert
    suspend fun insertAlarm(alarm: AlarmsEntity)

    @Delete
    suspend fun deleteAlarm(alarm: AlarmsEntity)

    @Query("SELECT * FROM alarmsentity order by time asc")
    fun getAlarms(): Flow<List<AlarmsEntity>>

    @Query("UPDATE alarmsentity SET enabled = :enabled WHERE uid = :alarmId")
    suspend fun toggleAlarm(alarmId: String, enabled: Boolean)
}