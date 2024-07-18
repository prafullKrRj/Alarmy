package com.prafull.alarmy.alarms.data.db

import androidx.room.Entity
import com.prafull.alarmy.alarms.domain.AmPm
import com.prafull.alarmy.alarms.domain.RepeatMode
import java.time.DayOfWeek
import java.util.UUID

@Entity(primaryKeys = ["uid"])
data class AlarmsEntity(
    val uid: String = UUID.randomUUID().toString(),
    val hours: Int,
    val minutes: Int,
    val enabled: Boolean,
    val message: String,
    val repeatMode: RepeatMode,
    val customDays: List<DayOfWeek> = emptyList(),
    val amPm: AmPm,
    val fullHour: Int
)