package com.example.kotlinhotel.presentation.hotelinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.data.repository.HotelRepository
import com.example.kotlinhotel.domain.model.Event
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HotelInfoViewModel(private val repository: HotelRepository) : ViewModel() {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    init {
        viewModelScope.launch {
            repository.getEvents()
                .catch { }
                .collect { _events.value = it }
        }
    }
}
