package com.example.flightsearchapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("airport")
data class Airport(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("iata_code") val iATACode: String = "",
    @ColumnInfo("name") val name: String = "",
    @ColumnInfo("passengers") val passengers: Int = 0
)
