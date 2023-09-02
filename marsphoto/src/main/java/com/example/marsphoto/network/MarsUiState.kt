package com.example.marsphoto.network

import com.example.marsphoto.data.MarsPhoto

sealed interface MarsUiState {

    data class Success (val photos: List<MarsPhoto>): MarsUiState
    object Loading: MarsUiState
    object Error : MarsUiState
}