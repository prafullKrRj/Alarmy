package com.prafull.alarmy

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data object AlarmScreen : Routes

    @Serializable
    data object AddAlarmScreen : Routes

    @Serializable
    data object SelectRingtoneScreen : Routes

    @Serializable
    data object AddClockScreen : Routes
}