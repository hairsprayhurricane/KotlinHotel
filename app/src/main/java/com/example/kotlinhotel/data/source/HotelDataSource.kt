package com.example.kotlinhotel.data.source

import com.example.kotlinhotel.domain.model.*

interface HotelDataSource {
    suspend fun getRooms(): List<Room>
    suspend fun getServices(): List<HotelService>
    suspend fun getReviews(): List<Review>
    suspend fun getEvents(): List<Event>
    suspend fun getCurrentUser(): User
    fun addReview(review: Review)
    fun addBooking(booking: Booking)
}
