package com.example.gamelog.screens.app.gamelib

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.data.model.game.GameDetail
import com.example.gamelog.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameLibViewModel(val navController: NavController): ViewModel() {

    private val _allGames = MutableStateFlow<List<GameDetail>>(emptyList())
    private val _isLoading = MutableStateFlow(false)

    val allGames = _allGames.asStateFlow()
    val isLoading = _isLoading.asStateFlow()

    fun loadAllGames() {
        _isLoading.value = true
        GameRepository.getAllGames { games ->
            _allGames.value = games
            _isLoading.value = false
        }
    }

    fun addGame() {
        navController.navigate("search")
    }
}