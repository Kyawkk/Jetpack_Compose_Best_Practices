package com.example.busschedule.data

import kotlinx.coroutines.flow.Flow

class ScheduleRepositoryImpl (private val busScheduleDao: BusScheduleDao) : ScheduleRepository {
    override fun getAllSchedules(): Flow<List<BusSchedule>> = busScheduleDao.getAllSchedules()
    override fun getSchedule(stopName: String): Flow<List<BusSchedule>> = busScheduleDao.getSchedule(stopName)
}