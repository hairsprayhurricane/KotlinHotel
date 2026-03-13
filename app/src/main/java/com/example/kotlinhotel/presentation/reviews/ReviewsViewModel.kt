package com.example.kotlinhotel.presentation.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.data.repository.HotelRepository
import com.example.kotlinhotel.domain.model.Review
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ReviewsViewModel(private val repository: HotelRepository) : ViewModel() {

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    init {
        loadReviews()
    }

    private fun loadReviews() {
        viewModelScope.launch {
            repository.getReviews()
                .catch { }
                .collect { _reviews.value = it }
        }
    }

    fun addReview(authorName: String, rating: Float, text: String) {
        val newReview = Review(
            id = System.currentTimeMillis().toInt(),
            authorName = authorName,
            rating = rating,
            text = text,
            date = java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault())
                .format(java.util.Date())
        )
        repository.addReview(newReview)
        _reviews.value = listOf(newReview) + (_reviews.value ?: emptyList())
    }
}
