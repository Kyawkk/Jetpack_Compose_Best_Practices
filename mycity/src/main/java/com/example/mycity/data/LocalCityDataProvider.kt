package com.example.mycity.data

import com.example.mycity.R
import com.example.mycity.model.Category

object LocalCityDataProvider {

    val categories = listOf(
        Category(
            1,
            "Category One",
            R.drawable.img,
            LocalRecommendPlacesDataProvider.recommendedPlaces
        ),

        Category(
            2,
            "Category Two",
            R.drawable.img,
            LocalRecommendPlacesDataProvider.recommendedPlacesTwo
        ),
        Category(
            3,
            "Category Three",
            R.drawable.img,
            LocalRecommendPlacesDataProvider.recommendedPlacesThree
        ),
        Category(
            4,
            "Category Four",
            R.drawable.img,
            LocalRecommendPlacesDataProvider.recommendedPlacesFour
        ),
    )
}