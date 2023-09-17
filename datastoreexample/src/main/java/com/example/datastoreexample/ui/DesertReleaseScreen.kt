@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.datastoreexample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.datastoreexample.R
import com.example.datastoreexample.data.local.LocalDesertReleaseData

@Composable
fun DesertReleaseApp(
    desertReleaseViewModel: DesertReleaseViewModel = viewModel(factory = DesertReleaseViewModel.Factory)
) {
    DesertReleaseScreen(uiState = desertReleaseViewModel.uiState.collectAsState().value, selectLayout = desertReleaseViewModel::selectLayout )
}

@Composable
private fun DesertReleaseScreen(
    uiState: DesertReleaseUiState,
    selectLayout: (Boolean) -> Unit
) {
    val isLinearLayout = uiState.isLinearLayout
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }, actions = {
                IconButton(onClick = { selectLayout(!isLinearLayout) }) {
                    Icon(
                        painter = painterResource(id = uiState.toggleIcon),
                        contentDescription = stringResource(
                            id = uiState.toggleContentDescription
                        )
                    )
                }
            }, colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary
            )
            )
        }
    ) {
        if (isLinearLayout) {
            DesertReleaseLinearLayout(modifier = Modifier.padding(it))
        } else {
            DesertReleaseGridLayout(Modifier.padding(it))
        }
    }
}

@Composable
fun DesertReleaseLinearLayout(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(LocalDesertReleaseData.desertReleases) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = it, modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DesertReleaseGridLayout(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = LocalDesertReleaseData.desertReleases,
            key = { desert -> desert }
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.height(110.dp)
            ) {
                Text(
                    text = it,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    DesertReleaseGridLayout()
}