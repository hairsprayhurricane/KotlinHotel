package com.example.kotlinhotel.domain.repository

import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.domain.model.ServiceCategory
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    fun getServices(): Flow<List<HotelService>>
    fun getServicesByCategory(category: ServiceCategory): Flow<List<HotelService>>
}
