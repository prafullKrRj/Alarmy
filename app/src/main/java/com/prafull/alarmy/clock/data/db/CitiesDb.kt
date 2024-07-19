package com.prafull.alarmy.clock.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CityTime::class], version = 1)
abstract class CitiesDb : RoomDatabase() {
    abstract fun citiesDao(): CitiesDao
}