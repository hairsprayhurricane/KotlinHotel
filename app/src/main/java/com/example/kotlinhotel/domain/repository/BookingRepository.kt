package com.example.kotlinhotel.domain.repository

import com.example.kotlinhotel.domain.model.Booking
import kotlinx.coroutines.flow.Flow

interface BookingRepository {
    fun getCurrentBooking(): Flow<Booking?>
    fun createBooking(booking: Booking): Flow<Result<Booking>>
}
