package com.prafull.alarmy.ui

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data object AlarmScreen : Routes

    @Serializable
    data object AddAlarmScreen : Routes

    @Serializable
    data object SelectRingtoneScreen : Routes

    @Serializable
    data object AddCityScreen : Routes
}