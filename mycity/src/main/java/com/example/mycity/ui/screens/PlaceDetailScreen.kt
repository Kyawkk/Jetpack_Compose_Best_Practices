package com.example.mycity.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycity.data.LocalRecommendPlacesDataProvider
import com.example.mycity.model.RecommendedPlace

@Composable
fun PlaceDetailScreen(
    recommendedPlace: RecommendedPlace,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Image(
            modifier = Modifier
                .height(200.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = recommendedPlace.placeImageResourceId),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = recommendedPlace.details
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PlaceDetailScreenPreview() {
    PlaceDetailScreen(recommendedPlace = LocalRecommendPlacesDataProvider.defaultRecommendedPlace)
}