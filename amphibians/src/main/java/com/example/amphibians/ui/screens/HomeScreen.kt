@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.amphibians.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibiansUiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    amphibiansUiState: AmphibiansUiState,
    onErrorClick: () -> Unit
) {
    when(amphibiansUiState){
        is AmphibiansUiState.Loading -> LoadingScreen()
        is AmphibiansUiState.Success -> HomeScreenContent(amphibians = amphibiansUiState.amphibians, modifier = modifier)
        is AmphibiansUiState.Error -> ErrorScreen (onErrorClick = onErrorClick)
    }
}

@Composable
fun LoadingScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        Text(text = "Loading...")
    }
}

@Composable
fun ErrorScreen(
    onErrorClick: () -> Unit
) {
   Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
       Column (horizontalAlignment = Alignment.CenterHorizontally) {
           Text(text = "Error!")
           Spacer(modifier = Modifier.height(16.dp))
           Button(onClick = onErrorClick) {
               Text(text = "Try Again")
           }
       }
   }
}

@Composable
fun HomeScreenContent(
    amphibians: List<Amphibian>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ){
        items(amphibians){amphibian ->
            AmphibianItemCard(amphibian = amphibian)
        }
    }
}

@Composable
fun AmphibianItemCard( amphibian: Amphibian, modifier: Modifier = Modifier ) {
    Card (
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        onClick = {}
    ) {
        Column {
            Text(
                text = "${amphibian.name} (${amphibian.type})",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .crossfade(true)
                    .data(amphibian.img_src)
                    .build(),
                modifier = Modifier.height(200.dp),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Text(
                text = amphibian.description,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AmphibianItemCardPreview() {
    AmphibianItemCard(amphibian = Amphibian(
        "Great Basin  Spadefoot",
        "Toad",
        "The following sections provide the general steps to create the app. You are not required to follow them, but they are provided to help guide you along the way.",
        ""
    ))
}