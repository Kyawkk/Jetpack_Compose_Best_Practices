package com.example.flightsearchapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapp.FlightSearchApplication
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.data.Favorite
import com.example.flightsearchapp.data.FavoriteRepository
import com.example.flightsearchapp.data.FlightSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightSearchViewModel (
    private val repository: FlightSearchRepository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(FlightSearchUiState())
    val uiState: StateFlow<FlightSearchUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                favorites = favoriteRepository.getAllFavorites(),
                title = "Favorite routes"
            )
        }
    }

    fun onValueChange(query: String) {
        _uiState.update {
            it.copy(
                query = query,
                flightScreenType = FlightScreenType.SEARCH,
                airports = if (query.trim().isNotEmpty()) repository.searchAirport("%$query%") else emptyFlow()
            )
        }
    }

    fun onSuggestionClick(airport: Airport){
        _uiState.update {
            it.copy(
                query = airport.iATACode,
                depart = airport,
                title = "Flights from ${airport.iATACode}",
                flightScreenType = FlightScreenType.DETAILS,
                airports = repository.getAllAirports()
            )
        }
    }

    fun addFavorite(favorite: Favorite){
        viewModelScope.launch {
            favoriteRepository.addFavorite(favorite)
        }
    }

    fun removeFavorite(favorite: Favorite){
        viewModelScope.launch {
            favoriteRepository.removeFavorite(favorite)
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application: FlightSearchApplication = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                FlightSearchViewModel(application.container.repository, application.container.favoriteRepository)
            }
        }
    }
}

enum class FlightScreenType{
    SEARCH,DETAILS,FAVORITE
}

data class FlightSearchUiState(
    val query: String = "",
    val flightScreenType: FlightScreenType = FlightScreenType.FAVORITE,
    val title: String = "",
    val airports: Flow<List<Airport>> = emptyFlow(),
    val favorites: Flow<List<Favorite>> = emptyFlow(),
    val depart: Airport = Airport()
)