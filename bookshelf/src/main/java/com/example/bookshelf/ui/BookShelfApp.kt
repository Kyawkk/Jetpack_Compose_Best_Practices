@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.bookshelf.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelf.R
import com.example.bookshelf.ui.screens.BookShelfContent

@Composable
fun BookShelfApp(
    booksUiState: BooksUiState,
    onErrorClick: () -> Unit
) {
    Scaffold(
        topBar = { BookShelfTopBar() }
    ) {
        when(booksUiState){
            is BooksUiState.Loading -> LoadingScreen()
            is BooksUiState.Success -> BookShelfContent(books = booksUiState.apiResponse.items, modifier = Modifier.padding(it))
            is BooksUiState.Error -> ErrorScreen(onErrorClick = onErrorClick)
        }
    }
}

@Composable
fun ErrorScreen(
    onErrorClick: () -> Unit
) {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
         Column {
             Text(text = "Error")
             Spacer(modifier = Modifier.height(16.dp))
             Button(onClick = { onErrorClick() }) {
                 Text(text = "Try Again")
             }
         }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Loading...")
    }
}

@Composable
fun BookShelfTopBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.Black
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}