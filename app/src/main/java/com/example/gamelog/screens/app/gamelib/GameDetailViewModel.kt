package com.example.gamelog.screens.app.gamelib

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.gamelog.base.Routes
import com.example.gamelog.data.api.RetrofitInstance
import com.example.gamelog.data.model.GameDetail
import com.example.gamelog.data.model.LocalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class GameDetailViewModel(val localData: LocalData, val navController: NavController, applicationContext: Context): ViewModel() {

    private val appContext = applicationContext.applicationContext

    private val _game = MutableStateFlow<GameDetail?>(null)
    val game: StateFlow<GameDetail?> = _game

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchGameDetail(gameId: Int) {
        if (_isLoading.value || (_game.value != null && _game.value?.id == gameId)) {
            Log.d("GameDetailViewModel", "Skipping fetch - already loading or data exists")
            return
        }

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
                Log.e("GameDetailViewModel", "Error fetching game detail", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addToLibrary(gameDetail: GameDetail) {
        // TODO: Add logic to save game to library

        Toast.makeText(
            appContext,
            "Adicionado com sucesso",
            Toast.LENGTH_SHORT
        ).show()

        navController.navigate(Routes.GameLib.route) {
            popUpTo(Routes.GameLib.route) { inclusive = true }
        }
    }
}