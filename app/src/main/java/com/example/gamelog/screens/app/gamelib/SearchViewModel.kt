package com.example.gamelog.screens.app.gamelib

import Game
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.gamelog.base.Routes
import com.example.gamelog.data.api.RetrofitInstance
import com.example.gamelog.data.model.LocalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SearchViewModel(val localData: LocalData, val navController: NavController): ViewModel() {

    private val _search = MutableStateFlow<String>("")
    val search: StateFlow<String> = _search

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun setSearch(query: String) {
        _search.value = query
    }

    private fun fetchGames() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                _errorMessage.value = null
                val response = RetrofitInstance.api.getGames(_search.value)
                _games.value = response.results
            } catch (e: Exception) {
                _errorMessage.value = when (e) {
                    is IOException -> "Network error. Check your connection."
                    is HttpException -> "Server error: ${e.code()}"
                    else -> "Unexpected error: ${e.message}"
                }
                Log.e("GameLibViewModel", "Error fetching games", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun search() {
        fetchGames()
    }

    fun gameDetail(id: Int?) {
        id?.let {
            navController.navigate(Routes.GameDetail.createRoute(it))
        }
    }

}