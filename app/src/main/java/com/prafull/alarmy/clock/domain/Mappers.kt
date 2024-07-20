package com.prafull.alarmy.clock.domain

import com.prafull.alarmy.clock.data.db.CityTime
import com.prafull.alarmy.clock.di.CityModel

fun CityTime.toCityModel() = CityModel(
    uid, city, country, timeZone, gmtZone
)

fun CityModel.toCityTime() = CityTime(
    uid, city, country, timeZone, gmtZone
)