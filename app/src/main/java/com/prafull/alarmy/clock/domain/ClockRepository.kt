package com.prafull.alarmy.clock.domain

import com.prafull.alarmy.clock.data.db.CityTime
import kotlinx.coroutines.flow.Flow

interface ClockRepository {
    fun getAllCities(): Flow<List<CityTime>>
    suspend fun insertCityTime(cityTime: CityTime)
    suspend fun deleteCityTime(cityTime: CityTime)
    suspend fun deleteSelected(cities: List<String>)
}