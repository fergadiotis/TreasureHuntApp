package com.tassos.treasurehuntapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TreasureHuntViewModel: ViewModel() {
    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _locations = MutableStateFlow(listOf(
        "City Hall",
        "Albion Falls",
        "Book Store",
        "Gage Park",
        "Pizza Place",
        "Tech World",
        "Dundurn Castle",
        "Library Lane",
        "Bel Canto Strings Academy",
        "Hamilton Scavenger Hunt",
        "Canadian Warplane Heritage Museum",
        "Craft Corner",
        "Hamilton Indoor Go Karts",
        "Coffee works",
        "Art Gallery of Hamilton",
        "Landmark Cinemas 6 Jackson Square",
        "Flying Squirrel Trampoline Park",
        "Bayfront Park",
        "limeridge mall",
        "Bubble Tea Bar",
        "Flower Shop",
        "Treasure Chest - Final Stop"
    ))
    val locations: StateFlow<List<String>> = _locations.asStateFlow()

    // need to get a map of the city and make it a static ping
    // save it as city_map.png and place in your drawables
    fun nextLocation() {
        if (_currentStep.value < _locations.value.size - 1) {
            _currentStep.value++
        }
    }
}