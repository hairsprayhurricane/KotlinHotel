package com.example.kotlinhotel.data.mock

import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.domain.model.ServiceCategory
import com.example.kotlinhotel.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockServiceRepository @Inject constructor(
    private val generator: MockDataGenerator
) : ServiceRepository {

    private val services: List<HotelService> = generator.generateServices()

    override fun getServices(): Flow<List<HotelService>> = flowOf(services)

    override fun getServicesByCategory(category: ServiceCategory): Flow<List<HotelService>> =
        flowOf(services.filter { it.category == category })
}
