package com.example.kotlinhotel.domain.model

data class Room(
    val id: Int,
    val number: String,
    val type: String,
    val floor: Int,
    val pricePerNight: Double,
    val description: String,
    val amenities: List<String> = emptyList(),
    val maxGuests: Int = 2
)
