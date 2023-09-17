package com.example.busschedule.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Schedule")
data class BusSchedule(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("stop_name") val stopName: String,
    @ColumnInfo("arrival_time") val arrivalTimeInMillis: Int
)
