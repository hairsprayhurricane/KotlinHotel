package com.example.kotlinhotel

import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.domain.model.ServiceCategory
import com.example.kotlinhotel.domain.model.UserPreferences
import com.example.kotlinhotel.presentation.home.HomeViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RecommendationTest {

    private fun makeServices(): List<HotelService> = listOf(
        HotelService("spa_001", "Massage", "desc", 1000.0, ServiceCategory.SPA),
        HotelService("spa_002", "Aromatherapy", "desc", 1200.0, ServiceCategory.SPA),
        HotelService("spa_003", "Hot Stone", "desc", 1500.0, ServiceCategory.SPA),
        HotelService("food_001", "Breakfast", "desc", 500.0, ServiceCategory.FOOD),
        HotelService("food_002", "Lunch", "desc", 800.0, ServiceCategory.FOOD),
        HotelService("exc_001", "City Tour", "desc", 2000.0, ServiceCategory.EXCURSION),
        HotelService("exc_002", "Wine Tour", "desc", 2500.0, ServiceCategory.EXCURSION),
    )

    private fun computeRecommendations(prefs: UserPreferences, services: List<HotelService>): List<HotelService> {
        if (prefs.orderedServiceIds.isEmpty()) return services.shuffled().take(3)
        val orderedCategories = services
            .filter { it.id in prefs.orderedServiceIds }
            .map { it.category }
        val topCategory = orderedCategories.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        return services.filter { it.category == topCategory && it.id !in prefs.orderedServiceIds }.take(3)
            .ifEmpty { services.shuffled().take(3) }
    }

    @Test
    fun `empty history returns 3 random services`() {
        val prefs = UserPreferences(userId = "u1")
        val services = makeServices()
        val result = computeRecommendations(prefs, services)
        assertEquals(3, result.size)
    }

    @Test
    fun `history with SPA orders recommends remaining SPA services`() {
        val services = makeServices()
        val prefs = UserPreferences(userId = "u1", orderedServiceIds = listOf("spa_001"))
        val result = computeRecommendations(prefs, services)
        assertTrue(result.all { it.category == ServiceCategory.SPA })
        assertTrue(result.none { it.id == "spa_001" })
    }

    @Test
    fun `top category has most ordered services`() {
        val services = makeServices()
        // 2 SPA orders vs 1 FOOD order → SPA should be top
        val prefs = UserPreferences(userId = "u1", orderedServiceIds = listOf("spa_001", "spa_002", "food_001"))
        val result = computeRecommendations(prefs, services)
        assertTrue(result.all { it.category == ServiceCategory.SPA })
        // spa_001 and spa_002 are in history, only spa_003 should be recommended
        assertTrue(result.any { it.id == "spa_003" })
    }

    @Test
    fun `all matching category already ordered falls back to random`() {
        val services = makeServices()
        // All SPA services are in history
        val prefs = UserPreferences(userId = "u1", orderedServiceIds = listOf("spa_001", "spa_002", "spa_003"))
        val result = computeRecommendations(prefs, services)
        assertEquals(3, result.size)
    }
}
