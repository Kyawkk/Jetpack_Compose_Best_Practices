package com.example.inventory.ui.home

import android.content.ClipData.Item
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(
    val itemList: List<Item> = listOf()
)