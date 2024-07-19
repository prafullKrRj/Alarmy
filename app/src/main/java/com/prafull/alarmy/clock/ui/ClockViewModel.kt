package com.prafull.alarmy.clock.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.alarmy.clock.domain.ClockRepository
import com.prafull.alarmy.clock.domain.toCityModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ClockViewModel : ViewModel(), KoinComponent {
    private val repository by inject<ClockRepository>()
    private val _cities =
        repository.getAllCities()
            .map { cities -> cities.map { cityEntity -> cityEntity.toCityModel() } }
            .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList())
    val cities = _cities

    fun deleteCities(cities: List<String>) {
        viewModelScope.launch {
            repository.deleteSelected(cities)
        }
    }
}