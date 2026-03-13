package com.example.kotlinhotel.presentation.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.domain.model.Review
import com.example.kotlinhotel.domain.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews

    val averageRating: Float
        get() = if (_reviews.value.isEmpty()) 0f
        else _reviews.value.map { it.rating }.average().toFloat()

    init {
        viewModelScope.launch {
            reviewRepository.getReviews().collect { reviews ->
                _reviews.value = reviews
            }
        }
    }

    fun submitReview(text: String, rating: Float) {
        val review = Review(
            id = UUID.randomUUID().toString(),
            authorName = "Guest",
            rating = rating,
            text = text,
            date = System.currentTimeMillis(),
            isOwn = true
        )
        viewModelScope.launch {
            reviewRepository.addReview(review)
        }
    }
}
