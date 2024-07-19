package com.prafull.alarmy.clock.di

import java.time.LocalDateTime

data class CityModel(
    val uid: String,
    val city: String,
    val country: String,
    val timeZone: String,
    val time: LocalDateTime? = null
)