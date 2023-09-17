@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.flightsearchapp.ui.screens

import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.R
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.ui.FlightScreenType
import com.example.flightsearchapp.ui.FlightSearchUiState
import com.example.flightsearchapp.ui.FlightSearchViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

@Composable
fun FlightSearchApp() {
    val viewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.Factory)
    val uiState = viewModel.uiState.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        FlightSearchAppContent(
            Modifier.padding(it),
            uiState = uiState,
            onValueChange = {
                viewModel.onValueChange(it)
            },
            onSuggestionClick = {
                viewModel.onSuggestionClick(it)
                Log.d("TAG", "isSearching: onClick")
            }
        )
    }
}

@Composable
fun FlightSearchAppContent(
    modifier: Modifier = Modifier,
    uiState: FlightSearchUiState,
    onValueChange: (String) -> Unit,
    onSuggestionClick: (Airport) -> Unit
) {

    val airports by uiState.airports.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize()
            .animateContentSize()
            .padding(16.dp)
    ) {
        FlightSearchView(
            onValueChange = onValueChange,
            query = uiState.query
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState.flightScreenType) {
            FlightScreenType.SEARCH -> {
                FlightSearchSuggestions(
                    airports = airports,
                    onSuggestionClick = { onSuggestionClick(it) }
                )
            }

            FlightScreenType.DETAILS -> {
                FlightListScreen(
                    depart = uiState.depart,
                    airports = airports,
                    title = uiState.title
                )
            }

            FlightScreenType.FAVORITE -> {

            }
        }


    }
}

@Composable
private fun FlightSearchView(
    modifier: Modifier = Modifier,
    query: String,
    onValueChange: (String) -> Unit
) {
    var textState: String by remember {
        mutableStateOf("")
    }
    TextField(
        value = query,
        onValueChange = {
            onValueChange(it)
            textState = it
        },
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        placeholder = { Text(text = "Search airport") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 8.dp)) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            }
        },
        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 8.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_mic_24),
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun FlightSearchSuggestions(
    onSuggestionClick: (Airport) -> Unit,
    airports: List<Airport>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(airports) {
            FlightSuggestionItem(
                airport = it,
                onSuggestionClick = { onSuggestionClick(it) })
        }
    }
}

@Composable
fun FlightSuggestionItem(
    airport: Airport,
    modifier: Modifier = Modifier,
    onSuggestionClick: (() -> Unit)? = {}
) {
    Row(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable(onClick = onSuggestionClick!!)
    ) {
        Text(text = airport.iATACode, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = airport.name)
    }
}

@Composable
private fun FlightListScreen(
    modifier: Modifier = Modifier,
    depart: Airport,
    airports: List<Airport>,
    title: String
) {
    Column(
        modifier = modifier
    ) {
        Text(text = title, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(airports) { arrive ->
                FlightListItem(depart, arrive)
            }
        }
    }
}

@Composable
fun FlightListItem(depart: Airport, arrive: Airport, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(
            topEnd = 16.dp,
            topStart = 0.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "DEPART")
                FlightSuggestionItem(airport = depart)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "ARRIVE")
                FlightSuggestionItem(airport = arrive)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = null)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FlightSearchAppPreview() {
    FlightListScreen(
        depart = Airport(0, "FRA", "Frankfurt Airport", 2343212),
        title = "",
        airports = listOf(
            Airport(0, "FRA", "Frankfurt Airport", 2343212),
            Airport(0, "FRA", "Frankfurt Airport", 2343212),
            Airport(0, "FRA", "Frankfurt Airport", 2343212),
            Airport(0, "FRA", "Frankfurt Airport", 2343212),
            Airport(0, "FRA", "Frankfurt Airport", 2343212)
        )
    )
}