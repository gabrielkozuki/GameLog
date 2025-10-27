package com.example.gamelog.base

sealed class Routes(val route: String) {
    // login
    data object Login: Routes("login")
    data object Register: Routes("register")
    data object ForgotPassword: Routes("forgotPassword")

    // app
    data object GameLib: Routes("gameLib")
    data object GameList: Routes("gameList")
    data object Review: Routes("review")
    data object Account: Routes("account")

}