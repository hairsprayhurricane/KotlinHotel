package com.example.kotlinhotel.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.data.repository.HotelRepository
import com.example.kotlinhotel.domain.model.Booking
import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.domain.model.User
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HotelRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _recommendations = MutableLiveData<List<HotelService>>()
    val recommendations: LiveData<List<HotelService>> = _recommendations

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getCurrentUser()
                .catch { /* handle error */ }
                .collect { user ->
                    _user.value = user
                    loadRecommendations(user.bookingHistory)
                }
        }
    }

    private fun loadRecommendations(history: List<Booking>) {
        viewModelScope.launch {
            repository.getRecommendations(history)
                .catch { /* handle error */ }
                .collect { _recommendations.value = it }
        }
    }
}
