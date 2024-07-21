package com.prafull.alarmy.alarms.di

import androidx.room.Room
import com.prafull.alarmy.alarms.data.AlarmReceiver
import com.prafull.alarmy.alarms.data.AlarmRepositoryImpl
import com.prafull.alarmy.alarms.data.AlarmScheduler
import com.prafull.alarmy.alarms.data.db.AlarmsDB
import com.prafull.alarmy.alarms.domain.AlarmsRepository
import com.prafull.alarmy.alarms.ui.AlarmsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val alarmModule = module {

    viewModel<AlarmsViewModel> {
        AlarmsViewModel()
    }
    single<AlarmsRepository> {
        AlarmRepositoryImpl()
    }
    single {
        Room.databaseBuilder(androidContext(), AlarmsDB::class.java, "alarms_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single<AlarmReceiver> {
        AlarmReceiver()
    }
    single<AlarmScheduler> {
        AlarmScheduler(get())
    }
    single { get<AlarmsDB>().alarmsDao() }
}