package com.example.busschedule.data

import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getAllSchedules(): Flow<List<BusSchedule>>
    fun getSchedule(stopName: String) : Flow<List<BusSchedule>>
}