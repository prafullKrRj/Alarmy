package com.prafull.alarmy.alarms.domain

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.UUID

enum class RepeatMode {
    ONCE, DAILY, MON_FRI, CUSTOM
}

data class AlarmItem(
    val uid: String = UUID.randomUUID().toString(),
    val time: LocalDateTime,
    val enabled: Boolean,
    val message: String = "",
    val repeatMode: RepeatMode = RepeatMode.ONCE,
    val customDays: List<DayOfWeek> = emptyList()
)