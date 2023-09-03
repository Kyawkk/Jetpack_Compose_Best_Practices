package com.example.bookshelf.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookShelfApplication
import com.example.bookshelf.network.BookShelfRepository
import kotlinx.coroutines.launch

class BookShelfViewModel(
    val repository: BookShelfRepository
): ViewModel() {
    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)

    init {
        getBooks()
    }

    fun getBooks() {
        viewModelScope.launch {
            booksUiState = try {
                BooksUiState.Success(repository.getBooks())
            }catch (e: Exception){
                BooksUiState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookShelfApplication)
                val repository = application.container.repository
                BookShelfViewModel(repository)
            }
        }
    }
}