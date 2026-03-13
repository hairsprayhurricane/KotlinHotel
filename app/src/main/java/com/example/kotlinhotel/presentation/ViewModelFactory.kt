package com.example.kotlinhotel.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinhotel.data.repository.HotelRepository

class ViewModelFactory(private val repository: HotelRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HotelRepository::class.java).newInstance(repository)
    }
}
