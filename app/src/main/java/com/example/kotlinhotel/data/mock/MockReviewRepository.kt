package com.example.kotlinhotel.data.mock

import com.example.kotlinhotel.domain.model.Review
import com.example.kotlinhotel.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockReviewRepository @Inject constructor(
    private val generator: MockDataGenerator
) : ReviewRepository {

    private val _reviews = MutableStateFlow<List<Review>>(generator.generateReviews())

    override fun getReviews(): Flow<List<Review>> = _reviews.asStateFlow()

    override suspend fun addReview(review: Review) {
        _reviews.update { listOf(review) + it }
    }
}
