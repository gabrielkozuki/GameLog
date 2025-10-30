package com.example.gamelog.data.model.review

data class Review(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val gameId: String = "",
    val gameTitle: String = "",
    val gameCoverUrl: String? = null,
    val rating: Float = 0f,
    val title: String = "",
    val content: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long? = null
)