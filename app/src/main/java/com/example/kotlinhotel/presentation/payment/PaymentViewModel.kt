package com.example.kotlinhotel.presentation.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PaymentState {
    object Idle : PaymentState()
    object Processing : PaymentState()
    object Success : PaymentState()
}

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow<PaymentState>(PaymentState.Idle)
    val state: StateFlow<PaymentState> = _state

    fun startPayment(service: HotelService) {
        if (_state.value == PaymentState.Processing) return
        _state.value = PaymentState.Processing
        viewModelScope.launch {
            delay(5_000)
            userRepository.addOrderedService(service.id)
            _state.value = PaymentState.Success
        }
    }
}
