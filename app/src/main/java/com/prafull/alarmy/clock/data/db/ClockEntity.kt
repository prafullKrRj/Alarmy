package com.prafull.alarmy.clock.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class CityTime(
    @PrimaryKey
    val uid: String = UUID.randomUUID().toString(),
    val city: String,
    val country: String,
    val timeZone: String,
    val gmtZone: String
)