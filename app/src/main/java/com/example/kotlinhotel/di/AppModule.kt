package com.example.kotlinhotel.di

import com.example.kotlinhotel.data.mock.MockBookingRepository
import com.example.kotlinhotel.data.mock.MockDataGenerator
import com.example.kotlinhotel.data.mock.MockEventRepository
import com.example.kotlinhotel.data.mock.MockReviewRepository
import com.example.kotlinhotel.data.mock.MockRoomRepository
import com.example.kotlinhotel.data.mock.MockServiceRepository
import com.example.kotlinhotel.data.mock.MockUserRepository
import com.example.kotlinhotel.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMockDataGenerator(): MockDataGenerator = MockDataGenerator()

    @Provides
    @Singleton
    fun provideRoomRepository(generator: MockDataGenerator): RoomRepository =
        MockRoomRepository(generator)

    @Provides
    @Singleton
    fun provideBookingRepository(
        generator: MockDataGenerator
    ): BookingRepository = MockBookingRepository(generator)

    @Provides
    @Singleton
    fun provideServiceRepository(generator: MockDataGenerator): ServiceRepository =
        MockServiceRepository(generator)

    @Provides
    @Singleton
    fun provideReviewRepository(generator: MockDataGenerator): ReviewRepository =
        MockReviewRepository(generator)

    @Provides
    @Singleton
    fun provideEventRepository(generator: MockDataGenerator): EventRepository =
        MockEventRepository(generator)

    @Provides
    @Singleton
    fun provideUserRepository(generator: MockDataGenerator): UserRepository =
        MockUserRepository(generator)
}
