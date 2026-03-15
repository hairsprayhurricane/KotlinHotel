package com.example.kotlinhotel.domain.repository

import com.example.kotlinhotel.domain.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getRooms(): Flow<List<Room>>
    fun getRoomById(id: String): Flow<Room?>
}
