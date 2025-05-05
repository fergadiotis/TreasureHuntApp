package com.tassos.treasurehuntapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TreasureHuntViewModel(application: Application) : AndroidViewModel(application) {
    private val database = TreasureHuntDatabase(application)

    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _locations = MutableStateFlow<List<LocationModel>>(emptyList())
    val locations: StateFlow<List<LocationModel>> = _locations.asStateFlow()

    init {
        loadLocations()
    }

    private fun loadLocations() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val locationsList = database.getAllLocations()
                _locations.value = locationsList

                // Set current step based on visited locations
                val visitedCount = locationsList.count { it.visited }
                if (visitedCount < locationsList.size) {
                    _currentStep.value = visitedCount
                }
            }
        }
    }

    fun nextLocation() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (_currentStep.value < _locations.value.size - 1) {
                    // Mark current location as visited
                    val currentLocation = _locations.value[_currentStep.value]
                    database.markLocationAsVisited(currentLocation.id)

                    // Update current step
                    _currentStep.value++

                    // Reload locations to reflect changes
                    loadLocations()
                }
            }
        }
    }

    fun resetHunt() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.resetAllLocations()
                _currentStep.value = 0
                loadLocations()
            }
        }
    }
}