package com.example.kotlinhotel.domain.model

data class Booking(
    val id: String,
    val room: Room,
    val guestName: String,
    val guestEmail: String,
    val guestPhone: String,
    val dateStart: Long,
    val dateEnd: Long,
    val status: BookingStatus
)

enum class BookingStatus {
    CONFIRMED, CHECKED_IN, COMPLETED
}
