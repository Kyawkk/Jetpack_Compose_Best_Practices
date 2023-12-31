package com.example.flightsearchapp.data

import kotlinx.coroutines.flow.Flow

class FlightSearchRepositoryImpl (private val airportDao: AirportDao) : FlightSearchRepository {
    override fun getAllAirports(): Flow<List<Airport>> = airportDao.getAllAirports()

    override fun getAirportDetail(iataCode: String): Airport = airportDao.getAirportDetail(iataCode)

    override fun searchAirport(query: String): Flow<List<Airport>> = airportDao.searchAirport(query)
}