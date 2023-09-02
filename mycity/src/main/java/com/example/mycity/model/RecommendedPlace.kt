package com.example.mycity.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class RecommendedPlace(
    val id: Long,
    @DrawableRes val placeImageResourceId: Int,
    val details: String
)
