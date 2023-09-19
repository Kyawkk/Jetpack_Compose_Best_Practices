package com.example.flightsearchapp.data

import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl (private val favoriteDao: FavoriteDao) : FavoriteRepository {
    override fun getAllFavorites(): Flow<List<Favorite>> = favoriteDao.getAllFavorite()
    override suspend fun addFavorite(favorite: Favorite) = favoriteDao.addFavorite(favorite)

    override fun isFavorite(
        departureCode: String,
        destinationCode: String
    ): Boolean = favoriteDao.getFavorite(departureCode, destinationCode) != null
    override suspend fun removeFavorite(favorite: Favorite) = favoriteDao.removeFavorite(favorite.destinationCode)
}