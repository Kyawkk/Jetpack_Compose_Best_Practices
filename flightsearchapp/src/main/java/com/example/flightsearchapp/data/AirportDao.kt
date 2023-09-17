package com.example.flightsearchapp.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("select * from airport")
    fun getAllAirports() : Flow<List<Airport>>

    @Query("select * from airport where name LIKE :query")
    fun searchAirport(query: String) : Flow<List<Airport>>
}