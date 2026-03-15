package com.example.kotlinhotel.domain.model

data class Review(
    val id: String,
    val authorName: String,
    val rating: Float,
    val text: String,
    val date: Long,
    val isOwn: Boolean = false
)
