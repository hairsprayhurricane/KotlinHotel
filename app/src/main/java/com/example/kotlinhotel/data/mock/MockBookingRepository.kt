package com.example.kotlinhotel.data.mock

import com.example.kotlinhotel.domain.model.Booking
import com.example.kotlinhotel.domain.repository.BookingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockBookingRepository @Inject constructor(
    private val generator: MockDataGenerator
) : BookingRepository {

    private val _booking = MutableStateFlow<Booking?>(null)

    override fun getCurrentBooking(): Flow<Booking?> = _booking.asStateFlow()

    override fun createBooking(booking: Booking): Flow<Result<Booking>> = flow {
        _booking.value = booking
        emit(Result.success(booking))
    }
}
