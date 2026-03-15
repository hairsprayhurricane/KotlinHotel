package com.example.kotlinhotel.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.domain.model.Booking
import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.domain.model.UserPreferences
import com.example.kotlinhotel.domain.repository.BookingRepository
import com.example.kotlinhotel.domain.repository.ServiceRepository
import com.example.kotlinhotel.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val booking: Booking? = null,
    val recommendations: List<HotelService> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val serviceRepository: ServiceRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(
                bookingRepository.getCurrentBooking(),
                serviceRepository.getServices(),
                userRepository.getUserPreferences()
            ) { booking, services, prefs ->
                HomeUiState(
                    booking = booking,
                    recommendations = computeRecommendations(prefs, services),
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun computeRecommendations(prefs: UserPreferences, services: List<HotelService>): List<HotelService> {
        if (prefs.orderedServiceIds.isEmpty()) return services.shuffled().take(3)
        val orderedCategories = services
            .filter { it.id in prefs.orderedServiceIds }
            .map { it.category }
        val topCategory = orderedCategories.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        return services.filter { it.category == topCategory && it.id !in prefs.orderedServiceIds }.take(3)
            .ifEmpty { services.shuffled().take(3) }
    }
}
