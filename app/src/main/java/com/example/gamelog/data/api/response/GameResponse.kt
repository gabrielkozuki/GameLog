package com.example.gamelog.data.api.response

import Game

data class GameResponse(
    val results: List<Game>,
    val count: Int
)