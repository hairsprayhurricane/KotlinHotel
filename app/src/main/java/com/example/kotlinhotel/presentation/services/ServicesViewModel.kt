package com.example.kotlinhotel.presentation.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.domain.model.ServiceCategory
import com.example.kotlinhotel.domain.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository
) : ViewModel() {

    private val _allServices = MutableStateFlow<List<HotelService>>(emptyList())
    val allServices: StateFlow<List<HotelService>> = _allServices

    init {
        viewModelScope.launch {
            serviceRepository.getServices().collect { services ->
                _allServices.value = services
            }
        }
    }

    fun getByCategory(category: ServiceCategory): List<HotelService> =
        _allServices.value.filter { it.category == category }
}
