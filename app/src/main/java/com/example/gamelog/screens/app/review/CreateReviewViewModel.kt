package com.example.gamelog.screens.app.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.data.model.game.GameDetail
import com.example.gamelog.data.model.review.Review
import com.example.gamelog.data.repository.GameRepository
import com.example.gamelog.data.repository.ReviewRepository
import com.google.firebase.auth.FirebaseAuth

class CreateReviewViewModel(val navController: NavController): ViewModel() {

    var availableGames by mutableStateOf<List<GameDetail>>(emptyList())
    var selectedGame by mutableStateOf<GameDetail?>(null)
    var rating by mutableStateOf(0f)
    var title by mutableStateOf("")
    var content by mutableStateOf("")
    var isLoadingGames by mutableStateOf(true)

    init {
        loadGames()
    }

    private fun loadGames() {
        isLoadingGames = true
        GameRepository.getAllGames { games ->
            availableGames = games
            isLoadingGames = false
        }
    }

    fun saveReview() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userName = FirebaseAuth.getInstance().currentUser?.displayName ?: "Anônimo"
        val game = selectedGame ?: return

        val review = Review(
            id = "",
            userId = userId,
            userName = userName,
            gameId = game.id?.toString() ?: "",
            gameTitle = game.name ?: "",
            gameCoverUrl = game.backgroundImage,
            rating = rating,
            title = title,
            content = content,
            createdAt = System.currentTimeMillis()
        )

        ReviewRepository.addReview(review)
        navController.popBackStack()
    }

}