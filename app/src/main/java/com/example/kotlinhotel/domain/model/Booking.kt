package com.example.kotlinhotel.domain.model

data class Booking(
    val id: Int,
    val roomNumber: String,
    val roomType: String,
    val floor: Int,
    val dateStart: String,
    val dateEnd: String,
    val status: String,
    val cleaningTime: String = "11:00"
)
