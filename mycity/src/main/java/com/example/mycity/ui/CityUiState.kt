package com.example.mycity.ui

import com.example.mycity.data.LocalCityDataProvider
import com.example.mycity.data.LocalRecommendPlacesDataProvider
import com.example.mycity.model.Category
import com.example.mycity.model.City
import com.example.mycity.model.RecommendedPlace

data class CityUiState(
    val categories: List<Category> = emptyList(),
    val recommendations: List<RecommendedPlace> = emptyList(),
    val placeDetail: RecommendedPlace = LocalRecommendPlacesDataProvider.defaultRecommendedPlace
)