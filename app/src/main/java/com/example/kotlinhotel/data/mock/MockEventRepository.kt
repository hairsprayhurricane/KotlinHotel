package com.example.kotlinhotel.data.mock

import com.example.kotlinhotel.domain.model.Event
import com.example.kotlinhotel.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockEventRepository @Inject constructor(
    private val generator: MockDataGenerator
) : EventRepository {

    override fun getEvents(): Flow<List<Event>> = flowOf(generator.generateEvents())
}
