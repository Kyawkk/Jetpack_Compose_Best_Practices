package com.example.bookshelf.ui

import com.example.bookshelf.model.BooksApiResponse

sealed interface BooksUiState {
    object Loading: BooksUiState
    data class Success(val apiResponse: BooksApiResponse): BooksUiState
    object Error: BooksUiState
}