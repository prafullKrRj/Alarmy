package com.prafull.alarmy.clock.domain

import androidx.room.Room
import com.prafull.alarmy.clock.data.ClockRepositoryImpl
import com.prafull.alarmy.clock.data.db.CitiesDb
import com.prafull.alarmy.clock.ui.ClockViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val clockModule = module {
    viewModel<ClockViewModel> {
        ClockViewModel()
    }
    single<ClockRepository> {
        ClockRepositoryImpl()
    }
    single {
        Room.databaseBuilder(androidContext(), CitiesDb::class.java, "cities_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<CitiesDb>().citiesDao() }
}