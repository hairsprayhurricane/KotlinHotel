package com.example.kotlinhotel.presentation.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.data.repository.HotelRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class PaymentUiState {
    object Idle : PaymentUiState()
    object Processing : PaymentUiState()
    object Success : PaymentUiState()
}

class PaymentViewModel(private val repository: HotelRepository) : ViewModel() {

    private val _uiState = MutableLiveData<PaymentUiState>(PaymentUiState.Idle)
    val uiState: LiveData<PaymentUiState> = _uiState

    private val _progress = MutableLiveData(0)
    val progress: LiveData<Int> = _progress

    fun processPayment() {
        if (_uiState.value == PaymentUiState.Processing) return
        viewModelScope.launch {
            _uiState.value = PaymentUiState.Processing
            for (i in 1..100) {
                delay(50L)
                _progress.postValue(i)
            }
            _uiState.value = PaymentUiState.Success
        }
    }
}
