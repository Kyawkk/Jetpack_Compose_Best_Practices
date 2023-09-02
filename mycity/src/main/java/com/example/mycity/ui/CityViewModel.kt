package com.example.mycity.ui

import androidx.lifecycle.ViewModel
import com.example.mycity.data.LocalCityDataProvider
import com.example.mycity.data.LocalRecommendPlacesDataProvider
import com.example.mycity.model.RecommendedPlace
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CityViewModel: ViewModel() {
    private val _cityUiState = MutableStateFlow(CityUiState())
    val cityUiState: StateFlow<CityUiState> = _cityUiState.asStateFlow()

    init {
        initializeData()
    }

    private fun initializeData() {
        _cityUiState.value = CityUiState(
            categories = LocalCityDataProvider.categories,
            recommendations = LocalRecommendPlacesDataProvider.recommendedPlaces,
            placeDetail = LocalRecommendPlacesDataProvider.defaultRecommendedPlace
        )
    }

    fun updateRecommendPlaces(recommendedPlaces: List<RecommendedPlace>){
        _cityUiState.update {
            it.copy(
               recommendations = recommendedPlaces
            )
        }
    }

    fun updatePlaceDetail(place: RecommendedPlace){
        _cityUiState.update {
            it.copy(
                placeDetail = place
            )
        }
    }
}