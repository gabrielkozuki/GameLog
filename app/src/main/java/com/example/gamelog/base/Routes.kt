package com.example.gamelog.base

sealed class Routes(val route: String) {
    // login
    data object Login: Routes("login")
    data object Register: Routes("register")
    data object ForgotPassword: Routes("forgotPassword")

    // game
    data object GameLib: Routes("gameLib")
    data object Search: Routes("search")

    data object GameDetail: Routes("gameDetail/{mode}/{id}") {
        fun createRoute(gameId: Int) = "gameDetail/create/$gameId"
        fun editRoute(firebaseId: String) = "gameDetail/edit/$firebaseId"
    }

    // list
    data object GameList: Routes("gameList") {
        fun createRoute() = "gameList/create/"
        fun editRoute(gameListId: String) = "gameList/edit/$gameListId"
        fun detailRoute(gameListId: String) = "gameList/detail/$gameListId"
    }

    // review
    data object Review: Routes("review") {
        fun createRoute() = "review/create/"
        fun editRoute(reviewId: String) = "review/edit/$reviewId"
        fun detailRoute(reviewId: String) = "review/detail/$reviewId"
    }

    // account
    // account
    data object Account: Routes("account") {
        fun editRoute() = "account/edit"
    }

}