package com.prafull.alarmy.alarms.mappers

import com.prafull.alarmy.alarms.data.db.AlarmsEntity
import com.prafull.alarmy.alarms.domain.AlarmItem

fun AlarmItem.toAlarmEntity(): AlarmsEntity = AlarmsEntity(
    uid = uid,
    time = time,
    message = message,
    enabled = enabled,
    repeatMode = repeatMode,
    customDays = customDays
)

fun AlarmsEntity.toAlarmItem(): AlarmItem = AlarmItem(
    uid = uid,
    time = time,
    message = message,
    enabled = enabled,
    repeatMode = repeatMode,
    customDays = customDays
)