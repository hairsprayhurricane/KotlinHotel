package com.example.kotlinhotel.domain.repository

import com.example.kotlinhotel.domain.model.User
import com.example.kotlinhotel.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): Flow<User>
    fun getUserPreferences(): Flow<UserPreferences>
    suspend fun addOrderedService(serviceId: String)
}
