package com.example.kotlinhotel.data.repository

import com.example.kotlinhotel.domain.model.*
import kotlinx.coroutines.flow.Flow

interface HotelRepository {
    fun getRooms(): Flow<List<Room>>
    fun getServices(): Flow<List<HotelService>>
    fun getReviews(): Flow<List<Review>>
    fun getEvents(): Flow<List<Event>>
    fun getCurrentUser(): Flow<User>
    fun addReview(review: Review)
    fun addBooking(booking: Booking)
    fun getRecommendations(bookingHistory: List<Booking>): Flow<List<HotelService>>
}
