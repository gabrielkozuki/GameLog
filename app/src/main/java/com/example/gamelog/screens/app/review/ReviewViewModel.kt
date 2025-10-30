package com.example.gamelog.screens.app.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.base.Routes
import com.example.gamelog.data.model.review.Review
import com.example.gamelog.data.repository.ReviewRepository

class ReviewViewModel(val navController: NavController): ViewModel() {

    var reviews by mutableStateOf<List<Review>>(emptyList())
        private set

    init {
        loadReviews()
    }

    private fun loadReviews() {
        ReviewRepository.getAllReviews { reviewsList ->
            reviews = reviewsList
        }
    }

    fun navigateToCreateReview() {
        navController.navigate(Routes.Review.createRoute())
    }

    fun navigateToDetailReview(reviewId: String) {
        navController.navigate(Routes.Review.detailRoute(reviewId))
    }
}
