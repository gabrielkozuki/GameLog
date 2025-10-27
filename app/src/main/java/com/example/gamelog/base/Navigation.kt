package com.example.gamelog.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamelog.model.LocalData

class Navigation {
    private lateinit var navController: NavHostController
    private lateinit var localData: LocalData

    @Composable
    fun Create() {
        navController = rememberNavController()
        localData = LocalData(LocalContext.current)

        NavHost(navController = navController, startDestination = Routes.Login.route) {
            // login
            composable(Routes.Login.route) {
                CallScaffold(localData, navController).CreateScreenLogin("login")
            }
            composable(Routes.Register.route) {
                CallScaffold(localData, navController).CreateScreenLogin("register")
            }
            composable(Routes.ForgotPassword.route) {
                CallScaffold(localData, navController).CreateScreenLogin("forgotPassword")
            }

            // app
            composable(Routes.GameLib.route) {
                CallScaffold(localData, navController).CreateScreenApp("gameLib")
            }
            composable(Routes.GameList.route) {
                CallScaffold(localData, navController).CreateScreenApp("gameList")
            }
            composable(Routes.Review.route) {
                CallScaffold(localData, navController).CreateScreenApp("review")
            }
            composable(Routes.Account.route) {
                CallScaffold(localData, navController).CreateScreenApp("account")
            }
        }
    }
}