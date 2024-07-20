package com.prafull.alarmy.clock.data

import com.prafull.alarmy.clock.data.db.CitiesDao
import com.prafull.alarmy.clock.data.db.CityTime
import com.prafull.alarmy.clock.domain.ClockRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ClockRepositoryImpl : KoinComponent, ClockRepository {

    private val clockDao by inject<CitiesDao>()
    override fun getAllCities(): Flow<List<CityTime>> {
        return clockDao.getAllCities()
    }

    override suspend fun insertCityTime(cityTime: CityTime) {
        clockDao.insertCityTime(cityTime)
    }

    override suspend fun deleteCityTime(cityTime: CityTime) {
        clockDao.deleteCityTime(cityTime)
    }

    override suspend fun deleteSelected(cities: List<String>) {
        clockDao.deleteSelected(cities)
    }
}