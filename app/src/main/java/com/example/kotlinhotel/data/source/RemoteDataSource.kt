package com.example.kotlinhotel.data.source

import com.example.kotlinhotel.domain.model.*

/**
 * Stub for future REST API implementation.
 * Replace MockDataSource with this class when backend is ready.
 */
class RemoteDataSource : HotelDataSource {
    override suspend fun getRooms(): List<Room> = throw UnsupportedOperationException("Not implemented yet")
    override suspend fun getServices(): List<HotelService> = throw UnsupportedOperationException("Not implemented yet")
    override suspend fun getReviews(): List<Review> = throw UnsupportedOperationException("Not implemented yet")
    override suspend fun getEvents(): List<Event> = throw UnsupportedOperationException("Not implemented yet")
    override suspend fun getCurrentUser(): User = throw UnsupportedOperationException("Not implemented yet")
    override fun addReview(review: Review) = throw UnsupportedOperationException("Not implemented yet")
    override fun addBooking(booking: Booking) = throw UnsupportedOperationException("Not implemented yet")
}
