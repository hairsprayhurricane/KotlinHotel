package com.example.kotlinhotel.data.mock

import com.example.kotlinhotel.domain.model.User
import com.example.kotlinhotel.domain.model.UserPreferences
import com.example.kotlinhotel.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockUserRepository @Inject constructor(
    private val generator: MockDataGenerator
) : UserRepository {

    private val user: User = generator.generateUser()
    private val _preferences = MutableStateFlow(UserPreferences(userId = user.id))

    override fun getCurrentUser(): Flow<User> = flowOf(user)

    override fun getUserPreferences(): Flow<UserPreferences> = _preferences.asStateFlow()

    override suspend fun addOrderedService(serviceId: String) {
        _preferences.update { prefs ->
            prefs.copy(orderedServiceIds = prefs.orderedServiceIds + serviceId)
        }
    }
}
