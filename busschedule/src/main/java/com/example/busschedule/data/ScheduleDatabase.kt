package com.example.busschedule.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BusSchedule::class], version = 4, exportSchema = false)
abstract class ScheduleDatabase : RoomDatabase() {
    abstract fun scheduleDao() : BusScheduleDao

    companion object{
        @Volatile
        private var Instance : ScheduleDatabase? = null

        fun getDatabase(context: Context): ScheduleDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, ScheduleDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("database/bus_schedule.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}