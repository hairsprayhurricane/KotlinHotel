package com.example.kotlinhotel.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String = "",
    val bookingHistory: List<Booking> = emptyList()
)
