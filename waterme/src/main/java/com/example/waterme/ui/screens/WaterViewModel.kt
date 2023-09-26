package com.example.waterme.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.waterme.WaterMeApplication
import com.example.waterme.data.DataSource
import com.example.waterme.data.Reminder
import com.example.waterme.data.WaterRepository
import com.example.waterme.data.WorkManagerRepository

class WaterViewModel (
    private val repository: WaterRepository
) : ViewModel() {
    internal val plants = repository.plants

    fun scheduleReminder(reminder: Reminder){
        repository.scheduleReminder(reminder.duration, reminder.unit,reminder.plantName)
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository = (this[APPLICATION_KEY] as WaterMeApplication).container.waterRepository
                WaterViewModel(repository)
            }
        }
    }
}