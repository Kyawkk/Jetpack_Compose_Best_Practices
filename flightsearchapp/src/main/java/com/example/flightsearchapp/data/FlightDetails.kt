package com.example.flightsearchapp.data

data class FlightDetails(
    val id: Int,
    val isFavorite: Boolean,
    val departureCode: String,
    val departureAirport: String,
    val arrivalCode: String,
    val arrivalAirport: String
)

fun FlightDetails.toDepartureAirport(): Airport = Airport(iATACode = this.departureCode, name = this.departureAirport)
fun FlightDetails.toDestinationAirport(): Airport = Airport(iATACode = this.arrivalCode, name = this.arrivalAirport)

fun FlightDetails.toFavorite(): Favorite = Favorite(departureCode = this.departureCode, destinationCode = this.arrivalCode)
