package com.example.gamelog.data.model.gamelist

data class GameList(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val games: List<GameListGame> = emptyList(),
    val tasks: List<Task> = emptyList(),
    val createdAt: Long = 0L,
    val updatedAt: Long? = null
)

data class GameListGame(
    val gameId: String = "",
    val gameTitle: String = "",
    val gameCoverUrl: String? = null
)