package com.example.gamelog.screens.app.gamelist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.data.model.game.GameDetail
import com.example.gamelog.data.model.gamelist.GameList
import com.example.gamelog.data.model.gamelist.GameListGame
import com.example.gamelog.data.repository.GameListRepository
import com.example.gamelog.data.repository.GameRepository

class CreateGameListViewModel(val navController: NavController): ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    val games = mutableStateListOf<GameListGame>()

    var titleError by mutableStateOf(false)
    var showGameSelectionDialog by mutableStateOf(false)
    var searchQuery by mutableStateOf("")

    private var allUserGames = mutableStateListOf<GameDetail>()
    val filteredGames = mutableStateListOf<GameDetail>()
    var isLoadingGames by mutableStateOf(false)

    init {
        loadUserGames()
    }

    private fun loadUserGames() {
        isLoadingGames = true
        GameRepository.getAllGames { userGames ->
            allUserGames.clear()
            allUserGames.addAll(userGames)
            filterGames()
            isLoadingGames = false
        }
    }

    private fun filterGames() {
        val query = searchQuery.trim().lowercase()
        val availableGames = allUserGames.filter { game ->
            !games.any { it.gameId == game.id.toString() }
        }

        filteredGames.clear()
        if (query.isEmpty()) {
            filteredGames.addAll(availableGames)
        } else {
            filteredGames.addAll(
                availableGames.filter { game ->
                    game.name?.lowercase()?.contains(query) == true
                }
            )
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
        filterGames()
    }

    fun openGameSelectionDialog() {
        searchQuery = ""
        loadUserGames()
        showGameSelectionDialog = true
    }

    fun closeGameSelectionDialog() {
        showGameSelectionDialog = false
        searchQuery = ""
    }

    fun createGameList() {
        if (title.isBlank()) {
            titleError = true
            return
        }

        val newGameList = GameList(
            title = title,
            description = description,
            games = games.toList(),
            tasks = emptyList()
        )

        GameListRepository.addGameList(newGameList)
        navController.popBackStack()
    }

    fun addGame(game: GameDetail) {
        val gameId = game.id?.toString() ?: return
        if (!games.any { it.gameId == gameId }) {
            games.add(GameListGame(
                gameId = gameId,
                gameTitle = game.name ?: "Sem título"
            ))
            filterGames()
        }
    }

    fun removeGame(gameId: String) {
        games.removeAll { it.gameId == gameId }
        filterGames()
    }
}