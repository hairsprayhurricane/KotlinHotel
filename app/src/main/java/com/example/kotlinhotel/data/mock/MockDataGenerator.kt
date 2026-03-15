package com.example.kotlinhotel.data.mock

import com.example.kotlinhotel.domain.model.*
import io.github.serpro69.kfaker.faker
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockDataGenerator @Inject constructor() {

    private val faker = faker { }

    fun generateRooms(count: Int = 12): List<Room> {
        val types = listOf("Standard", "Deluxe", "Suite", "Junior Suite")
        return (1..count).map { i ->
            Room(
                id = UUID.randomUUID().toString(),
                number = "${(1..5).random()}${(10..99).random()}",
                type = types[(i - 1) % types.size],
                floor = (1..10).random(),
                pricePerNight = (50..500).random().toDouble(),
                description = faker.lorem.words() + " " + faker.lorem.words() + " comfortable room with modern amenities.",
                amenities = generateAmenities()
            )
        }
    }

    private fun generateAmenities(): List<String> {
        val all = listOf("WiFi", "TV", "Mini-bar", "Safe", "Jacuzzi", "Sea View", "Air Conditioning", "Balcony")
        return all.shuffled().take((2..5).random())
    }

    fun generateServices(): List<HotelService> = listOf(
        HotelService(
            id = "spa_001",
            title = "Classic Massage",
            description = faker.lorem.words() + " relaxing full-body massage session.",
            price = 2500.0,
            category = ServiceCategory.SPA
        ),
        HotelService(
            id = "spa_002",
            title = "Aromatherapy",
            description = "Soothing aromatherapy with essential oils.",
            price = 3000.0,
            category = ServiceCategory.SPA
        ),
        HotelService(
            id = "spa_003",
            title = "Hot Stone Massage",
            description = "Deep relaxation with heated volcanic stones.",
            price = 3500.0,
            category = ServiceCategory.SPA
        ),
        HotelService(
            id = "food_001",
            title = "Continental Breakfast",
            description = "Fresh pastries, fruits, coffee and juice.",
            price = 800.0,
            category = ServiceCategory.FOOD
        ),
        HotelService(
            id = "food_002",
            title = "Business Lunch",
            description = "Three-course lunch with daily specials.",
            price = 1200.0,
            category = ServiceCategory.FOOD
        ),
        HotelService(
            id = "food_003",
            title = "Gala Dinner",
            description = "Fine dining experience with live music.",
            price = 2800.0,
            category = ServiceCategory.FOOD
        ),
        HotelService(
            id = "transfer_001",
            title = "Airport Transfer",
            description = "Comfortable sedan transfer to/from airport.",
            price = 1500.0,
            category = ServiceCategory.TRANSFER
        ),
        HotelService(
            id = "transfer_002",
            title = "City Tour Transfer",
            description = "Guided city tour with personal driver.",
            price = 2000.0,
            category = ServiceCategory.TRANSFER
        ),
        HotelService(
            id = "exc_001",
            title = "City Sightseeing Tour",
            description = "Half-day guided tour of historic city center.",
            price = 1800.0,
            category = ServiceCategory.EXCURSION
        ),
        HotelService(
            id = "exc_002",
            title = "Wine Tasting Experience",
            description = "Visit local vineyards and taste premium wines.",
            price = 2500.0,
            category = ServiceCategory.EXCURSION
        ),
        HotelService(
            id = "exc_003",
            title = "Mountain Hiking Tour",
            description = "Full-day guided hike with breathtaking views.",
            price = 3200.0,
            category = ServiceCategory.EXCURSION
        ),
        HotelService(
            id = "laundry_001",
            title = "Express Laundry",
            description = "Same-day laundry and pressing service.",
            price = 600.0,
            category = ServiceCategory.LAUNDRY
        ),
        HotelService(
            id = "cleaning_001",
            title = "Room Deep Cleaning",
            description = "Thorough deep cleaning of your room.",
            price = 500.0,
            category = ServiceCategory.CLEANING
        )
    )

    fun generateReviews(count: Int = 15): List<Review> {
        val now = System.currentTimeMillis()
        val ninetyDaysMs = 90L * 24 * 60 * 60 * 1000
        return (1..count).map {
            Review(
                id = UUID.randomUUID().toString(),
                authorName = faker.name.name(),
                rating = (30..50).random() / 10f,
                text = faker.lorem.words() + " " + faker.lorem.words() + " Great experience!",
                date = now - (0..ninetyDaysMs).random()
            )
        }
    }

    fun generateEvents(count: Int = 8): List<Event> {
        val times = listOf("09:00", "10:00", "14:00", "16:00", "18:00", "19:00", "20:00", "21:00")
        val locations = listOf("Main Hall", "Pool Area", "Restaurant", "Conference Room", "Lobby", "Garden")
        return (1..count).map { i ->
            Event(
                id = UUID.randomUUID().toString(),
                title = faker.lorem.words() + " " + faker.lorem.words() + " Event",
                time = times[(i - 1) % times.size],
                location = locations[(i - 1) % locations.size],
                description = faker.lorem.words() + " Join us for this special event."
            )
        }
    }

    fun generateUser(): User = User(
        id = UUID.randomUUID().toString(),
        name = faker.name.name(),
        email = faker.internet.email(),
        phone = "+7 (900) ${(100..999).random()}-${(10..99).random()}-${(10..99).random()}"
    )

    fun generateCurrentBooking(rooms: List<Room>): Booking {
        val now = System.currentTimeMillis()
        val dayMs = 24L * 60 * 60 * 1000
        return Booking(
            id = UUID.randomUUID().toString(),
            room = rooms[0],
            guestName = faker.name.name(),
            guestEmail = faker.internet.email(),
            guestPhone = "+7 (900) ${(100..999).random()}-${(10..99).random()}-${(10..99).random()}",
            dateStart = now - 2 * dayMs,
            dateEnd = now + 3 * dayMs,
            status = BookingStatus.CHECKED_IN
        )
    }
}
