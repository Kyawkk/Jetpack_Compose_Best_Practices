package com.example.flightsearchapp.data

class FavoriteRepositoryImpl (private val favoriteDao: FavoriteDao) : FavoriteRepository {
    override fun getAllFavorites() = favoriteDao.getAllFavorite()
    override suspend fun addFavorite(favorite: Favorite) = favoriteDao.addFavorite(favorite)
    override suspend fun removeFavorite(favorite: Favorite) = favoriteDao.removeFavorite(favorite)
}