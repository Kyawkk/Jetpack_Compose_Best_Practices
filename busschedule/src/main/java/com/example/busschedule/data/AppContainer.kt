package com.example.busschedule.data

import android.content.Context

interface AppContainer {
    val schedulesRepository: ScheduleRepository
}

class AppDataContainer(private val context: Context) : AppContainer{
    override val schedulesRepository: ScheduleRepository by lazy {
        ScheduleRepositoryImpl(ScheduleDatabase.getDatabase(context).scheduleDao())
    }
}