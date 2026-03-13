package com.example.kotlinhotel.data.mock

import com.example.kotlinhotel.domain.model.Room
import com.example.kotlinhotel.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockRoomRepository @Inject constructor(
    private val generator: MockDataGenerator
) : RoomRepository {

    private val rooms: List<Room> = generator.generateRooms()

    override fun getRooms(): Flow<List<Room>> = flowOf(rooms)

    override fun getRoomById(id: String): Flow<Room?> = flowOf(rooms.find { it.id == id })
}
