package com.example.gamelog.base

sealed class Routes(val route: String) {
    // login
    data object Login: Routes("login")
    data object Register: Routes("register")
    data object ForgotPassword: Routes("forgotPassword")

    // app
    data object GameLib: Routes("gameLib")
    data object Search: Routes("search")

    data object GameDetail: Routes("gameDetail/{mode}/{id}") {
        fun createRoute(gameId: Int) = "gameDetail/create/$gameId"
        fun editRoute(firebaseId: String) = "gameDetail/edit/$firebaseId"
    }

    data object GameList: Routes("gameList")
    data object Review: Routes("review")
    data object Account: Routes("account")

}