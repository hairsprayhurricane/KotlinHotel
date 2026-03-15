package com.example.kotlinhotel.presentation.rooms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.domain.model.Room
import com.example.kotlinhotel.domain.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _allRooms = MutableStateFlow<List<Room>>(emptyList())
    private val _filteredRooms = MutableStateFlow<List<Room>>(emptyList())
    val rooms: StateFlow<List<Room>> = _filteredRooms

    private val _selectedFilter = MutableStateFlow("All")
    val selectedFilter: StateFlow<String> = _selectedFilter

    init {
        viewModelScope.launch {
            roomRepository.getRooms().collect { rooms ->
                _allRooms.value = rooms
                applyFilter(_selectedFilter.value)
            }
        }
    }

    fun setFilter(type: String) {
        _selectedFilter.value = type
        applyFilter(type)
    }

    private fun applyFilter(type: String) {
        _filteredRooms.value = if (type == "All") {
            _allRooms.value
        } else {
            _allRooms.value.filter { it.type.equals(type, ignoreCase = true) }
        }
    }
}
