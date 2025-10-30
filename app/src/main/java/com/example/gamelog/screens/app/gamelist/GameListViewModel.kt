package com.example.gamelog.screens.app.gamelist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.base.Routes
import com.example.gamelog.data.model.gamelist.GameList
import com.example.gamelog.data.repository.GameListRepository

class GameListViewModel(val navController: NavController): ViewModel() {

    var gameLists by mutableStateOf<List<GameList>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        loadGameLists()
    }

    private fun loadGameLists() {
        isLoading = true
        GameListRepository.getAllGameLists { lists ->
            gameLists = lists.sortedByDescending { it.createdAt }
            isLoading = false
        }
    }

    fun navigateToCreate() {
        navController.navigate(Routes.GameList.createRoute())
    }

    fun navigateToDetail(gameListId: String) {
        navController.navigate(Routes.GameList.detailRoute(gameListId))
    }

}