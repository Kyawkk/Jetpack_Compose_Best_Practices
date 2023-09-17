package com.example.busschedule.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.busschedule.BusScheduleApplication
import com.example.busschedule.data.BusSchedule
import com.example.busschedule.data.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BusScheduleViewModel (private val repository: ScheduleRepository): ViewModel() {
    fun getFullSchedule(): Flow<List<BusSchedule>> = repository.getAllSchedules()

    fun getScheduleFor(stopName: String): Flow<List<BusSchedule>> = repository.getSchedule(stopName)

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { BusScheduleViewModel(inventoryApplication().container.schedulesRepository) }
        }

        fun CreationExtras.inventoryApplication(): BusScheduleApplication =
            (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BusScheduleApplication)
    }
}