package com.example.bluromatic.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.WorkInfo
import com.example.bluromatic.BluromaticApplication
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.data.BlurAmountData
import com.example.bluromatic.data.BluromaticRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BlurViewModel(val repository: BluromaticRepository) : ViewModel() {
    internal val blurAmount = BlurAmountData.blurAmount
    val blurUiState: StateFlow<BlurUiState> = repository.outputWorkInfo.map { info ->
        val outputImageUri = info.outputData.getString(KEY_IMAGE_URI)
        when {
            info.state.isFinished -> {
                BlurUiState.Complete(outputUri = outputImageUri.toString())
            }

            info.state == WorkInfo.State.CANCELLED -> {
                BlurUiState.Default
            }

            else -> BlurUiState.Loading
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), BlurUiState.Default)

    fun applyBlur(blurLevel: Int) {
        repository.applyBlur(blurLevel)
    }

    fun cancelBlur() = repository.cancelWork()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository = (this[APPLICATION_KEY] as BluromaticApplication).repository
                BlurViewModel(repository)
            }
        }
    }
}

sealed interface BlurUiState {
    object Default : BlurUiState
    object Loading : BlurUiState
    data class Complete(val outputUri: String) : BlurUiState
}