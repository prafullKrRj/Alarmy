package com.prafull.alarmy.alarms.mappers

import com.prafull.alarmy.alarms.data.db.AlarmsEntity
import com.prafull.alarmy.alarms.domain.AlarmItem

fun AlarmItem.toAlarmEntity(): AlarmsEntity = AlarmsEntity(
    uid = uid,
    hours = hours,
    minutes = minutes,
    message = message,
    enabled = enabled,
    repeatMode = repeatMode,
    customDays = customDays,
    amPm = amPm,
    fullHour = fullHour
)

fun AlarmsEntity.toAlarmItem(): AlarmItem = AlarmItem(
    uid = uid,
    hours = hours,
    minutes = minutes,
    message = message,
    enabled = enabled,
    repeatMode = repeatMode,
    customDays = customDays,
    amPm = amPm,
    fullHour = fullHour
)