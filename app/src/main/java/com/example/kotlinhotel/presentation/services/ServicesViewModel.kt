package com.example.kotlinhotel.presentation.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.data.repository.HotelRepository
import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.domain.model.ServiceCategory
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ServicesViewModel(private val repository: HotelRepository) : ViewModel() {

    private val _allServices = MutableLiveData<List<HotelService>>()
    val allServices: LiveData<List<HotelService>> = _allServices

    init {
        loadServices()
    }

    fun loadServices() {
        viewModelScope.launch {
            repository.getServices()
                .catch { /* handle */ }
                .collect { _allServices.value = it }
        }
    }

    fun getServicesByCategory(category: ServiceCategory): List<HotelService> {
        return _allServices.value?.filter { it.category == category } ?: emptyList()
    }
}
