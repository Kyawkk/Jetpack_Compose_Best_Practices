package com.example.flightsearchapp.data

import android.content.Context

interface AppContainer {
    val repository: FlightSearchRepository
    val favoriteRepository: FavoriteRepository
}

class AppDataContainer (context: Context) : AppContainer{
    override val repository : FlightSearchRepository by lazy {
        FlightSearchRepositoryImpl(FlightSearchDatabase.getDatabase(context).airportDao())
    }

    override val favoriteRepository: FavoriteRepository by lazy {
        FavoriteRepositoryImpl(FlightSearchDatabase.getDatabase(context).favoriteDao())
    }
}