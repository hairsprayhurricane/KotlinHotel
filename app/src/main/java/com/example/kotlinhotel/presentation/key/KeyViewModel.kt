package com.example.kotlinhotel.presentation.key

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.domain.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class DoorState { LOCKED, UNLOCKING, OPEN }

@HiltViewModel
class KeyViewModel @Inject constructor(
    private val bookingRepository: BookingRepository
) : ViewModel() {

    private val _doorState = MutableStateFlow(DoorState.LOCKED)
    val doorState: StateFlow<DoorState> = _doorState

    private val _roomNumber = MutableStateFlow("")
    val roomNumber: StateFlow<String> = _roomNumber

    init {
        viewModelScope.launch {
            bookingRepository.getCurrentBooking().collect { booking ->
                _roomNumber.value = booking?.room?.number ?: "---"
            }
        }
    }

    fun simulateUnlock() {
        if (_doorState.value == DoorState.UNLOCKING || _doorState.value == DoorState.OPEN) return
        viewModelScope.launch {
            _doorState.value = DoorState.UNLOCKING
            delay(1500)
            _doorState.value = DoorState.OPEN
            delay(3000)
            _doorState.value = DoorState.LOCKED
        }
    }
}
