package com.prafull.alarmy.alarms.data.db

import androidx.room.TypeConverter
import com.prafull.alarmy.alarms.domain.RepeatMode
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime?): String? {
        return localDateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return dateTimeString?.let {
            LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
    }
}

class DayOfWeekListConverter {

    @TypeConverter
    fun fromDayOfWeekList(days: List<DayOfWeek>?): String? {
        return days?.joinToString(separator = ",") { it.name }
    }

    @TypeConverter
    fun toDayOfWeekList(data: String?): List<DayOfWeek>? {
        return data?.split(",")?.mapNotNull { dayName ->
            try {
                DayOfWeek.valueOf(dayName)
            } catch (e: IllegalArgumentException) {
                null // Ignore or handle invalid day names as needed
            }
        }
    }
}

class RepeatModeConverter {
    @TypeConverter
    fun fromRepeatMode(repeatMode: RepeatMode?): String? {
        return repeatMode?.name
    }

    @TypeConverter
    fun toRepeatMode(repeatModeString: String?): RepeatMode? {
        return repeatModeString?.let { RepeatMode.valueOf(it) }
    }
}