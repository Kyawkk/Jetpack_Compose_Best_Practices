package com.example.marsphoto.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphoto.MarsPhotosApplication
import com.example.marsphoto.data.MarsPhotosRepository
import com.example.marsphoto.data.NetworkMarsPhotosRepository
import com.example.marsphoto.network.MarsUiState
import kotlinx.coroutines.launch
import java.io.IOException

class MarsViewModel (
    private val marsPhotosRepository: MarsPhotosRepository
): ViewModel() {
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val listResult = marsPhotosRepository.getMarsPhotos()
                marsUiState = MarsUiState.Success(listResult)
            }catch (e: IOException){
                marsUiState = MarsUiState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
            }
        }
    }
}