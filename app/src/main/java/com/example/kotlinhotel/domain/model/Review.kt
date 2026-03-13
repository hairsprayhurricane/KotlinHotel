package com.example.kotlinhotel.domain.model

data class Review(
    val id: Int,
    val authorName: String,
    val rating: Float,
    val text: String,
    val date: String
)
