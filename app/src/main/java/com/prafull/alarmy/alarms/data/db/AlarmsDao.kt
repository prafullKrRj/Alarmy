package com.prafull.alarmy.alarms.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface AlarmsDao {


    @Transaction
    suspend fun insertAlarmIfNotExists(alarm: AlarmsEntity) {
        val count = checkAlarmExists(alarm.fullHour, alarm.minutes)
        if (count == 0) {
            insertAlarm(alarm)
        }
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAlarm(alarm: AlarmsEntity)

    @Query("SELECT COUNT(*) FROM alarmsentity WHERE fullHour = :fullHour AND minutes = :minutes")
    suspend fun checkAlarmExists(fullHour: Int, minutes: Int): Int

    @Delete
    suspend fun deleteAlarm(alarm: AlarmsEntity)

    @Query("SELECT * FROM alarmsentity order by fullHour, minutes asc")
    fun getAlarms(): Flow<List<AlarmsEntity>>

    @Query("UPDATE alarmsentity SET enabled = :enabled WHERE uid = :alarmId")
    suspend fun toggleAlarm(alarmId: String, enabled: Boolean)

    @Query("DELETE FROM alarmsentity WHERE uid IN (:toList)")
    suspend fun deleteAlarms(toList: List<String>)
}