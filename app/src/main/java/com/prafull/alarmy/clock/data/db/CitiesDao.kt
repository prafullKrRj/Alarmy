package com.prafull.alarmy.clock.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface CitiesDao {

    @Upsert
    suspend fun insertCityTime(cityTime: CityTime)

    @Delete
    suspend fun deleteCityTime(cityTime: CityTime)

    @Query("DELETE FROM citytime WHERE uid IN (:cities)")
    suspend fun deleteSelected(cities: List<String>)

    @Query("SELECT * FROM citytime")
    fun getAllCities(): Flow<List<CityTime>>
}