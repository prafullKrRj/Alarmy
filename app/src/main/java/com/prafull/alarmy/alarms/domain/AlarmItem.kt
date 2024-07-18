package com.prafull.alarmy.alarms.domain

import java.time.DayOfWeek
import java.util.UUID

enum class RepeatMode {
    ONCE, DAILY, MON_FRI, CUSTOM
}

enum class AmPm {
    AM, PM
}

data class AlarmItem(
    val uid: String = UUID.randomUUID().toString(),
    val hours: Int = 6,
    val minutes: Int = 30,
    val enabled: Boolean = true,
    val message: String = "",
    val repeatMode: RepeatMode = RepeatMode.ONCE,
    val customDays: List<DayOfWeek> = emptyList(),
    val amPm: AmPm = AmPm.AM,
    val fullHour: Int = 6
)