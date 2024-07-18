package com.prafull.alarmy.alarms.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [AlarmsEntity::class], version = 1)
@TypeConverters(
    AmPmConverter::class,
    DayOfWeekListConverter::class,
    RepeatModeConverter::class
)
abstract class AlarmsDB : RoomDatabase() {
    abstract fun alarmsDao(): AlarmsDao
}