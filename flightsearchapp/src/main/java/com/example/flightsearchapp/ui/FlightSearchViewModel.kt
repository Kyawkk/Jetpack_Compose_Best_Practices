package com.example.flightsearchapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapp.FlightSearchApplication
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.data.Favorite
import com.example.flightsearchapp.data.FavoriteRepository
import com.example.flightsearchapp.data.FlightDetails
import com.example.flightsearchapp.data.FlightSearchRepository
import com.example.flightsearchapp.data.toFavorite
import com.example.flightsearchapp.utils.toFlowList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlightSearchViewModel(
    private val repository: FlightSearchRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FlightSearchUiState())
    val uiState: StateFlow<FlightSearchUiState> = _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        //val favorites = favoriteRepository.getAllFavorites()

        viewModelScope.launch {
            favoriteRepository.getAllFavorites().collect{
                val flightDetailsList = mutableListOf<FlightDetails>()
                it.forEach {
                    CoroutineScope(Dispatchers.IO).launch {
                        val depart = repository.getAirportDetail(it.departureCode)
                        val arrive = repository.getAirportDetail(it.destinationCode)
                        flightDetailsList.add(
                            FlightDetails(
                                id = it.id,
                                departureCode = it.departureCode,
                                departureAirport = depart.name,
                                arrivalCode = it.destinationCode,
                                arrivalAirport = arrive.name,
                                isFavorite = true
                            )
                        )

                        withContext(Dispatchers.Main) {
                            _uiState.update {
                                it.copy(
                                    title = "Favorite routes",
                                    favoriteFlightDetailsList = flightDetailsList.toFlowList(),
                                )
                            }
                        }
                    }
                }
            }
        }


    }

    fun onValueChange(query: String) {

        if (query.isEmpty()) loadFavorites()

        _uiState.update {
            it.copy(
                query = query,
                flightScreenType = if (query.isNotEmpty()) FlightScreenType.SEARCH else FlightScreenType.FAVORITE,
                airports = if (query.trim()
                        .isNotEmpty()
                ) repository.searchAirport("%$query%") else emptyFlow()
            )
        }
    }

    fun onSuggestionClick(depart: Airport) {

        val airports = repository.getAllAirports()
        val convertedList = mutableListOf<FlightDetails>()

        viewModelScope.launch {
            convertedList.clear()
            airports.collect{

                it.map { arrive ->
                    CoroutineScope(Dispatchers.IO).launch {
                        val isFavorite = favoriteRepository.isFavorite(depart.iATACode, arrive.iATACode)
                        convertedList.add(
                            FlightDetails(
                                id = depart.id,
                                departureCode = depart.iATACode,
                                departureAirport = depart.name,
                                arrivalCode = arrive.iATACode,
                                arrivalAirport = arrive.name,
                                isFavorite = isFavorite
                            )
                        )

                        withContext(Dispatchers.Main) {
                            _uiState.update {
                                it.copy(
                                    query = depart.iATACode,
                                    depart = depart,
                                    flightScreenType = FlightScreenType.DETAILS,
                                    title = "Flights from ${depart.iATACode}",
                                    airports = airports,
                                    flightDetailsList = convertedList.toFlowList()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun toggleFavorite(flightDetails: FlightDetails) {
        viewModelScope.launch (Dispatchers.IO) {
            if (flightDetails.isFavorite) favoriteRepository.removeFavorite(flightDetails.toFavorite())
            else favoriteRepository.addFavorite(flightDetails.toFavorite())
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application: FlightSearchApplication =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                FlightSearchViewModel(
                    application.container.repository, application.container.favoriteRepository
                )
            }
        }
    }
}

enum class FlightScreenType {
    SEARCH, DETAILS, FAVORITE
}

data class FlightSearchUiState(
    val query: String = "",
    val flightScreenType: FlightScreenType = FlightScreenType.FAVORITE,
    val title: String = "",
    val airports: Flow<List<Airport>> = emptyFlow(),
    val favorites: Flow<List<Favorite>> = emptyFlow(),
    val favoriteFlightDetailsList: Flow<List<FlightDetails>> = emptyFlow(),
    val flightDetailsList: Flow<List<FlightDetails>> = emptyFlow(),
    val currentFavorite: Favorite = Favorite(),
    val isFavorite: Boolean = false,
    val depart: Airport = Airport()
)
