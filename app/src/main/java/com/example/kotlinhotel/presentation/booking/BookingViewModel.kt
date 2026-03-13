package com.example.kotlinhotel.presentation.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.data.repository.HotelRepository
import com.example.kotlinhotel.domain.model.Booking
import com.example.kotlinhotel.domain.model.Room
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BookingViewModel(private val repository: HotelRepository) : ViewModel() {

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> = _rooms

    private val _selectedRoom = MutableLiveData<Room?>()
    val selectedRoom: LiveData<Room?> = _selectedRoom

    private val _selectedDates = MutableLiveData<Pair<String, String>?>()
    val selectedDates: LiveData<Pair<String, String>?> = _selectedDates

    private val _bookingSuccess = MutableLiveData(false)
    val bookingSuccess: LiveData<Boolean> = _bookingSuccess

    init {
        loadRooms()
    }

    private fun loadRooms() {
        viewModelScope.launch {
            repository.getRooms()
                .catch { /* handle */ }
                .collect { _rooms.value = it }
        }
    }

    fun selectRoom(room: Room) {
        _selectedRoom.value = room
    }

    fun setDates(start: String, end: String) {
        _selectedDates.value = Pair(start, end)
    }

    fun confirmBooking(guestName: String, phone: String, email: String, passport: String) {
        val room = _selectedRoom.value ?: return
        val dates = _selectedDates.value ?: return

        val booking = Booking(
            id = System.currentTimeMillis().toInt(),
            roomNumber = room.number,
            roomType = room.type,
            floor = room.floor,
            dateStart = dates.first,
            dateEnd = dates.second,
            status = "Подтверждено"
        )
        repository.addBooking(booking)
        _bookingSuccess.value = true
    }
}
