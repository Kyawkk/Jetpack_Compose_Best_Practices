package com.example.flightsearchapp.data

import kotlinx.coroutines.flow.Flow

interface FlightSearchRepository {
    fun getAllAirports() : Flow<List<Airport>>
    fun searchAirport(query: String) : Flow<List<Airport>>
}