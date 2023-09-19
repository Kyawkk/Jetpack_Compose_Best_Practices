package com.example.flightsearchapp.data

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorites() : Flow<List<Favorite>>
    suspend fun addFavorite(favorite: Favorite)

    fun isFavorite(departureCode: String, destinationCode: String) : Boolean
    suspend fun removeFavorite(favorite: Favorite)
}