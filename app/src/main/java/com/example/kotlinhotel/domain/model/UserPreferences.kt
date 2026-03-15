package com.example.kotlinhotel.domain.model

data class UserPreferences(
    val userId: String,
    val orderedServiceIds: List<String> = emptyList()
)
