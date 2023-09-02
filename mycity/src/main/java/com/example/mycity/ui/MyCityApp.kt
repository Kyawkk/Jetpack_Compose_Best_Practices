@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mycity.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycity.R
import com.example.mycity.data.LocalCityDataProvider
import com.example.mycity.data.LocalRecommendPlacesDataProvider
import com.example.mycity.ui.screens.CategoryScreen
import com.example.mycity.ui.screens.PlaceDetailScreen
import com.example.mycity.utils.MyCityScreen

@Composable
fun MyCityApp(
    navController: NavHostController = rememberNavController()
) {
    val viewModel: CityViewModel = viewModel()
    val uiState by viewModel.cityUiState.collectAsState()

    var selectedDestination by remember {
        mutableStateOf(MyCityScreen.Category.name)
    }
    BackHandler {

    }
    Scaffold (
        topBar = {
            MyCityAppBar(
            canNavigateUp = selectedDestination != MyCityScreen.Category.name,
                onBackButtonClicked = {
                    //navController.navigate()
                }
            )
        }
    ) {

        NavHost(
            navController = navController,
            startDestination = MyCityScreen.Category.name,
            modifier = Modifier.padding(it)
        ){
            composable(route = MyCityScreen.Category.name){
                CategoryScreen(
                    categories = uiState.categories,
                    onItemClick = {
                        navController.navigate(MyCityScreen.RecommendedPlace.name)
                        selectedDestination = MyCityScreen.RecommendedPlace.name

                        viewModel.updateRecommendPlaces(
                            when(it.categoryTitleId){
                                "Category One" -> LocalRecommendPlacesDataProvider.recommendedPlaces
                                "Category Two" -> LocalRecommendPlacesDataProvider.recommendedPlacesTwo
                                "Category Three" -> LocalRecommendPlacesDataProvider.recommendedPlacesThree
                                "Category Four" -> LocalRecommendPlacesDataProvider.recommendedPlacesFour

                                else -> LocalRecommendPlacesDataProvider.recommendedPlaces
                            }
                        )
                    }
                )
            }
            composable(route = MyCityScreen.RecommendedPlace.name){
                CategoryScreen(
                    categories = uiState.categories,
                    onItemClick = {
                        navController.navigate(MyCityScreen.PlaceDetail.name)
                        selectedDestination = MyCityScreen.PlaceDetail.name

                        //viewModel.updatePlaceDetail()
                    }
                )
            }
            composable(route = MyCityScreen.PlaceDetail.name){
                PlaceDetailScreen(
                    recommendedPlace = uiState.placeDetail
                )
            }
        }
    }
}

@Composable
fun MyCityAppBar(
    modifier: Modifier = Modifier,
    canNavigateUp: Boolean = false,
    onBackButtonClicked: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = R.string.app_name))},
        colors = TopAppBarDefaults.smallTopAppBarColors(),
        navigationIcon = {
            if (canNavigateUp){
                IconButton(onClick = { onBackButtonClicked }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
@Preview (showSystemUi = true, showBackground = true)
fun MyCityAppPreview() {
    MyCityApp()
}