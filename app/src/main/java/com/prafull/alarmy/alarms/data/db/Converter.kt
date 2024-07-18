package com.prafull.alarmy.alarms.data.db

import androidx.room.TypeConverter
import com.prafull.alarmy.alarms.domain.AmPm
import com.prafull.alarmy.alarms.domain.RepeatMode
import java.time.DayOfWeek


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

class AmPmConverter {
    @TypeConverter
    fun fromAmPm(amPm: AmPm?): String? {
        return amPm?.name
    }

    @TypeConverter
    fun toAmPm(string: String?): AmPm? {
        return string?.let { AmPm.valueOf(it) }
    }
}