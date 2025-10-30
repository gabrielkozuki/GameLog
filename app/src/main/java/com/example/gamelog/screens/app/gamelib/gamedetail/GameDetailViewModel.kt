package com.example.gamelog.screens.app.gamelib.gamedetail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.gamelog.base.Routes
import com.example.gamelog.data.api.RetrofitInstance
import com.example.gamelog.data.model.game.GameDetail
import com.example.gamelog.data.model.game.GameStatus
import com.example.gamelog.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class GameDetailViewModel(
    val navController: NavController,
    context: Context
) : ViewModel() {

    private val appContext = context.applicationContext

    private val _game = MutableStateFlow<GameDetail?>(null)
    val game: StateFlow<GameDetail?> = _game

    private val _mode = MutableStateFlow<GameDetailMode>(GameDetailMode.Create(0))
    val mode: StateFlow<GameDetailMode> = _mode

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun initializeScreen(mode: GameDetailMode) {
        _mode.value = mode
        when (mode) {
            is GameDetailMode.Create -> fetchGameFromApi(mode.gameId)
            is GameDetailMode.Edit -> fetchGameFromFirebase(mode.firebaseGameId.toInt())
        }
    }

    private fun fetchGameFromApi(gameId: Int) {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                _errorMessage.value = null
                val response = RetrofitInstance.api.getGameDetail(gameId)
                _game.value = response
            } catch (e: Exception) {
                _errorMessage.value = when (e) {
                    is IOException -> "Network error. Check your connection."
                    is HttpException -> "Server error: ${e.code()}"
                    else -> "Unexpected error: ${e.message}"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun fetchGameFromFirebase(firebaseGameId: Int) {
        _isLoading.value = true
        GameRepository.getGame(firebaseGameId) { gameDetail ->
            _game.value = gameDetail
            _isLoading.value = false
        }
    }

    fun addToLibrary(gameDetail: GameDetail) {
        try {
            GameRepository.addGame(gameDetail, status = GameStatus.BACKLOG)

            Toast.makeText(
                appContext,
                "Jogo adicionado ao seu Backlog",
                Toast.LENGTH_SHORT
            ).show()

            navController.navigate(Routes.GameLib.route) {
                popUpTo(Routes.GameLib.route) { inclusive = true }
            }
        } catch (e: Exception) {
            Toast.makeText(
                appContext,
                "Erro ao adicionar o jogo: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun updateGame(gameDetail: GameDetail) {
        try {
            GameRepository.updateGame(gameDetail)

            Toast.makeText(
                appContext,
                "Jogo atualizado com sucesso",
                Toast.LENGTH_SHORT
            ).show()

            navController.popBackStack()
        } catch (e: Exception) {
            Toast.makeText(
                appContext,
                "Erro ao atualizar o jogo: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun deleteGame(firebaseGameId: Int) {
        try {
            GameRepository.deleteGame(firebaseGameId)

            Toast.makeText(
                appContext,
                "Jogo removido da biblioteca",
                Toast.LENGTH_SHORT
            ).show()

            navController.navigate(Routes.GameLib.route) {
                popUpTo(Routes.GameLib.route) { inclusive = true }
            }
        } catch (e: Exception) {
            Toast.makeText(appContext,
                "Erro ao remover o jogo: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}