package com.example.flightsearchapp.data

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorites() : Flow<List<Favorite>>
    suspend fun addFavorite(favorite: Favorite)
    suspend fun removeFavorite(favorite: Favorite)
}