package com.example.kotlinhotel.domain.repository

import com.example.kotlinhotel.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    fun getReviews(): Flow<List<Review>>
    suspend fun addReview(review: Review)
}
