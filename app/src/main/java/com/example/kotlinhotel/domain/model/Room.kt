package com.example.kotlinhotel.domain.model

data class Room(
    val id: String,
    val number: String,
    val type: String,
    val floor: Int,
    val pricePerNight: Double,
    val description: String,
    val amenities: List<String>,
    val imageRes: Int = 0,
    val cleaningTime: String = "11:00"
)
