package com.example.amphibians.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibianApplication
import com.example.amphibians.network.AmphibiansRepository
import com.example.amphibians.network.AmphibiansUiState
import com.example.amphibians.network.AmphibiansUiState.Success
import com.example.amphibians.network.NetworkAmphibiansRepository
import kotlinx.coroutines.launch

class AmphibianViewModel (
    val repository: AmphibiansRepository
): ViewModel() {

    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set

    init {
        getAmphibians()
    }

    fun getAmphibians() {
        viewModelScope.launch {
            amphibiansUiState = try {
                AmphibiansUiState.Loading
                Success(repository.getAmphibians())
            }catch (e: Exception){
                AmphibiansUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibianApplication)
                val repository = application.container.repository
                AmphibianViewModel(repository)
            }
        }
    }
}