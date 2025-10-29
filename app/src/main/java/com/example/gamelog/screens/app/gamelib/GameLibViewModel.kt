package com.example.gamelog.screens.app.gamelib

import Game
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.data.model.LocalData

class GameLibViewModel(val localData: LocalData, val navController: NavController): ViewModel() {

    private val _games = mutableStateOf<List<Game>>(emptyList())
    val games: State<List<Game>> = _games

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun addGame() {
        navController.navigate("search")
    }

}