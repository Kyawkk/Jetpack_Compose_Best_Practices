package com.example.flightsearchapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("select * from favorite")
    fun getAllFavorite() : Flow<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)

    @Query("select * from favorite where departure_code = :departureCode and destination_code = :destinationCode")
    fun getFavorite(departureCode: String, destinationCode: String): Favorite?

    @Query("delete from favorite where destination_code = :destinationCode")
    fun removeFavorite(destinationCode: String)
}