package com.example.flightsearchapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @ColumnInfo("departure_code")
    val departureCode: String = "",
    @ColumnInfo("destination_code")
    val destinationCode: String = ""
)

fun Favorite.toDepart(): Airport{
    return Airport(
        id = this.id,
        iATACode = this.departureCode
    )
}
