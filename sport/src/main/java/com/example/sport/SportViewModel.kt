package com.example.sport

import androidx.lifecycle.ViewModel
import com.example.sport.data.LocalSportsDataProvider
import com.example.sport.model.Sport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SportViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        SportUiState(
            sportList = LocalSportsDataProvider.getSportsData(),
            currentSport = LocalSportsDataProvider.getSportsData().getOrElse(0){
                LocalSportsDataProvider.defaultSport
            }
        )
    )
    val uiState: StateFlow<SportUiState> = _uiState.asStateFlow()

    fun updateCurrentSport(selectedSport: Sport){
        _uiState.update {
            it.copy(
                currentSport = selectedSport
            )
        }
    }

    fun navigateToListPage(){
        _uiState.update {
            it.copy(
                isShowingListPage = true
            )
        }
    }

    fun navigateToDetailPage(){
        _uiState.update {
            it.copy(isShowingListPage = false)
        }
    }
}

data class SportUiState(
    val sportList: List<Sport> = emptyList(),
    val currentSport: Sport = LocalSportsDataProvider.defaultSport,
    val isShowingListPage: Boolean = true
)