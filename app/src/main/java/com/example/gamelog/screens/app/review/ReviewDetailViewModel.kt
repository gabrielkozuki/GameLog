package com.example.gamelog.screens.app.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.base.Routes
import com.example.gamelog.data.model.review.Review
import com.example.gamelog.data.repository.ReviewRepository

class ReviewDetailViewModel(val navController: NavController): ViewModel() {

    var review by mutableStateOf<Review?>(null)
        private set

    fun loadReview(reviewId: String) {
        ReviewRepository.getReview(reviewId) { loadedReview ->
            review = loadedReview
        }
    }

    fun navigateToEdit(reviewId: String) {
        navController.navigate(Routes.Review.editRoute(reviewId))
    }
}
