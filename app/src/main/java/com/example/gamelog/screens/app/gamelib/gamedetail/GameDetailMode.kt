package com.example.gamelog.screens.app.gamelib.gamedetail

sealed class GameDetailMode {
    data class Create(val gameId: Int) : GameDetailMode()
    data class Edit(val firebaseGameId: String) : GameDetailMode()
}