package com.example.kotlinhotel.presentation.rooms

import androidx.lifecycle.SavedStateHandle
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
class RoomDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val roomId: String = savedStateHandle["roomId"] ?: ""

    private val _room = MutableStateFlow<Room?>(null)
    val room: StateFlow<Room?> = _room

    init {
        viewModelScope.launch {
            roomRepository.getRoomById(roomId).collect { room ->
                _room.value = room
            }
        }
    }
}
