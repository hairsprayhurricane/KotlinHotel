package com.example.kotlinhotel.presentation.key

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.data.repository.HotelRepository
import com.example.kotlinhotel.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class KeyViewModel(private val repository: HotelRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _doorOpened = MutableLiveData(false)
    val doorOpened: LiveData<Boolean> = _doorOpened

    init {
        viewModelScope.launch {
            repository.getCurrentUser()
                .catch { }
                .collect { _user.value = it }
        }
    }

    fun simulateNfcTap() {
        viewModelScope.launch {
            delay(800)
            _doorOpened.value = true
            delay(3000)
            _doorOpened.value = false
        }
    }
}
