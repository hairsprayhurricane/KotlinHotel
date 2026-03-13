package com.example.kotlinhotel.presentation.rooms

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.domain.model.Booking
import com.example.kotlinhotel.domain.model.BookingStatus
import com.example.kotlinhotel.domain.repository.BookingRepository
import com.example.kotlinhotel.domain.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class BookingFormState {
    object Idle : BookingFormState()
    object Loading : BookingFormState()
    object Success : BookingFormState()
    data class Error(val message: String) : BookingFormState()
}

@HiltViewModel
class BookingFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val roomRepository: RoomRepository,
    private val bookingRepository: BookingRepository
) : ViewModel() {

    private val roomId: String = savedStateHandle["roomId"] ?: ""
    private val _state = MutableStateFlow<BookingFormState>(BookingFormState.Idle)
    val state: StateFlow<BookingFormState> = _state

    fun submitBooking(name: String, email: String, phone: String) {
        viewModelScope.launch {
            _state.value = BookingFormState.Loading
            val room = roomRepository.getRoomById(roomId).first()
            if (room == null) {
                _state.value = BookingFormState.Error("Room not found")
                return@launch
            }
            val now = System.currentTimeMillis()
            val booking = Booking(
                id = UUID.randomUUID().toString(),
                room = room,
                guestName = name,
                guestEmail = email,
                guestPhone = phone,
                dateStart = now,
                dateEnd = now + 3 * 24 * 60 * 60 * 1000L,
                status = BookingStatus.CONFIRMED
            )
            bookingRepository.createBooking(booking).collect { result ->
                _state.value = if (result.isSuccess) BookingFormState.Success
                else BookingFormState.Error(result.exceptionOrNull()?.message ?: "Error")
            }
        }
    }
}
