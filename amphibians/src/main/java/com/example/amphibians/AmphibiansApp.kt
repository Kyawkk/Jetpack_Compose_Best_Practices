@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.amphibians

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amphibians.ui.AmphibianViewModel
import com.example.amphibians.ui.screens.HomeScreen

@Composable
fun AmphibiansApp() {
    val viewModel: AmphibianViewModel = viewModel(factory = AmphibianViewModel.Factory)
    Surface {
        Scaffold (
            topBar = { AmphibiansTopBar() }
        ) {
            HomeScreen(
                modifier = Modifier.padding(it),
                amphibiansUiState = viewModel.amphibiansUiState,
                onErrorClick = {viewModel.getAmphibians()}
            )
        }
    }
}

@Composable
fun AmphibiansTopBar() {
    TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun AmphibiansAppPreview() {
    AmphibiansApp()
}