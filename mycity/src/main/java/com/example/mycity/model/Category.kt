package com.example.mycity.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
    val id: Long,
    val categoryTitleId: String,
    @DrawableRes val categoryImageId: Int,
    val recommendations: List<RecommendedPlace> = emptyList()
)
