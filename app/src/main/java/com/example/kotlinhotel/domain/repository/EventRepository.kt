package com.example.kotlinhotel.domain.repository

import com.example.kotlinhotel.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvents(): Flow<List<Event>>
}
