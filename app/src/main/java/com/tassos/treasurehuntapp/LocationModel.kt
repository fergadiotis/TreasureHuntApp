package com.tassos.treasurehuntapp

data class LocationModel(
    val id: Int,
    val name: String,
    val description: String,
    val visited: Boolean,
    val order: Int
)