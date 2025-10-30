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

class EditReviewViewModel(val navController: NavController): ViewModel() {

    var review by mutableStateOf<Review?>(null)
        private set

    var availableGames by mutableStateOf<List<GameDetail>>(emptyList())
    var selectedGame by mutableStateOf<GameDetail?>(null)
    var rating by mutableStateOf(0f)
    var title by mutableStateOf("")
    var content by mutableStateOf("")
    var isLoadingGames by mutableStateOf(true)

    var showDeleteDialog by mutableStateOf(false)
        private set

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

    fun loadReview(reviewId: String) {
        ReviewRepository.getReview(reviewId) { loadedReview ->
            review = loadedReview
            loadedReview?.let {
                // Encontra o jogo correspondente na lista
                selectedGame = availableGames.find { game ->
                    game.id?.toString() == it.gameId
                }
                rating = it.rating
                title = it.title
                content = it.content
            }
        }
    }

    fun updateReview() {
        val currentReview = review ?: return
        val game = selectedGame ?: return

        val updatedReview = currentReview.copy(
            gameId = game.id?.toString() ?: "",
            gameTitle = game.name ?: "",
            gameCoverUrl = game.backgroundImage,
            rating = rating,
            title = title,
            content = content
        )

        ReviewRepository.updateReview(updatedReview)
        navController.popBackStack()
    }

    fun showDeleteConfirmation() {
        showDeleteDialog = true
    }

    fun dismissDeleteDialog() {
        showDeleteDialog = false
    }

    fun confirmDelete() {
        review?.id?.let { id ->
            ReviewRepository.deleteReview(id)
            showDeleteDialog = false
            navController.popBackStack()
        }
    }

}
