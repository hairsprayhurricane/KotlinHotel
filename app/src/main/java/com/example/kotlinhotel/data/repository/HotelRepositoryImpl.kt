package com.example.kotlinhotel.data.repository

import com.example.kotlinhotel.data.source.HotelDataSource
import com.example.kotlinhotel.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HotelRepositoryImpl(private val dataSource: HotelDataSource) : HotelRepository {

    override fun getRooms(): Flow<List<Room>> = flow {
        emit(dataSource.getRooms())
    }

    override fun getServices(): Flow<List<HotelService>> = flow {
        emit(dataSource.getServices())
    }

    override fun getReviews(): Flow<List<Review>> = flow {
        emit(dataSource.getReviews())
    }

    override fun getEvents(): Flow<List<Event>> = flow {
        emit(dataSource.getEvents())
    }

    override fun getCurrentUser(): Flow<User> = flow {
        emit(dataSource.getCurrentUser())
    }

    override fun addReview(review: Review) {
        dataSource.addReview(review)
    }

    override fun addBooking(booking: Booking) {
        dataSource.addBooking(booking)
    }

    override fun getRecommendations(bookingHistory: List<Booking>): Flow<List<HotelService>> = flow {
        val allServices = dataSource.getServices()
        if (bookingHistory.isEmpty()) {
            emit(allServices.shuffled().take(5))
        } else {
            // Recommend based on previously ordered service categories
            val recommended = allServices.shuffled().take(5)
            emit(recommended)
        }
    }
}
