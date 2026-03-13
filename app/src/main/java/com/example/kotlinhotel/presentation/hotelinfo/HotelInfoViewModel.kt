package com.example.kotlinhotel.presentation.hotelinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.domain.model.Event
import com.example.kotlinhotel.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelInfoViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    init {
        viewModelScope.launch {
            eventRepository.getEvents().collect { events ->
                _events.value = events
            }
        }
    }
}
