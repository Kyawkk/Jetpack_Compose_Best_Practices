package com.example.datastoreexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.datastoreexample.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DesertReleaseViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DesertReleaseUiState())

    val uiState : StateFlow<DesertReleaseUiState> = _uiState.asStateFlow()

    fun selectLayout (isLinearLayout: Boolean) {
        _uiState.value = DesertReleaseUiState(isLinearLayout = isLinearLayout)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { DesertReleaseViewModel() }
        }
    }
}

data class DesertReleaseUiState(
    val isLinearLayout : Boolean = true,
    val toggleContentDescription : Int = if(isLinearLayout) R.string.grid_layout_toggle else R.string.linear_layout_toggle,
    val toggleIcon : Int = if (isLinearLayout) R.drawable.ic_grid_layout else R.drawable.ic_linear_layout
)