package com.example.marsphoto.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marsphoto.data.MarsPhoto
import com.example.marsphoto.network.MarsUiState

@Composable
fun HomeScreen(
    marsUiState: MarsUiState
) {
    when (marsUiState){
        is MarsUiState.Loading -> LoadingScreen(modifier = Modifier)
        is MarsUiState.Success -> ResultScreen(photos = marsUiState.photos, modifier = Modifier.fillMaxWidth())
        is MarsUiState.Error -> Text(text = "Error")
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
    )
}

@Composable
fun ResultScreen(
    photos: List<MarsPhoto>,
    modifier: Modifier = Modifier
) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        LazyVerticalGrid (
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(photos){photo ->
                MarsPhotoItem(marsPhoto = photo)
            }
        }
    }
}

@Composable
fun MarsPhotoItem(marsPhoto: MarsPhoto, modifier: Modifier = Modifier) {
    Card (
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.size(120.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(marsPhoto.img_src)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null)
    }
}