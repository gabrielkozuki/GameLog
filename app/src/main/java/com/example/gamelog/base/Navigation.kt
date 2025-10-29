package com.example.gamelog.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gamelog.data.model.LocalData
import com.example.gamelog.screens.app.gamelib.GameDetailViewModel

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
                CallScaffold(localData, navController).CreateScreen("login")
            }
            composable(Routes.Register.route) {
                CallScaffold(localData, navController).CreateScreen("register")
            }
            composable(Routes.ForgotPassword.route) {
                CallScaffold(localData, navController).CreateScreen("forgotPassword")
            }

            // app
            composable(Routes.GameLib.route) {
                CallScaffold(localData, navController).CreateScreenApp()
            }
            composable(Routes.Search.route) {
                CallScaffold(localData, navController).CreateScreen("search")
            }
            composable(
                route = Routes.GameDetail.route,
                arguments = listOf(navArgument("gameId") { type = NavType.IntType })
            ) { backStackEntry ->
                val context = LocalContext.current
                val gameId = backStackEntry.arguments?.getInt("gameId") ?: 0
                val viewModel: GameDetailViewModel = viewModel(
                    factory = ViewModelFactory(localData, navController, context.applicationContext)
                )

                CallScaffold(localData, navController).CreateGameDetailScreen(
                    viewModel = viewModel,
                    gameId = gameId
                )
            }

            composable(Routes.GameList.route) {
                CallScaffold(localData, navController).CreateScreenApp()
            }
            composable(Routes.Review.route) {
                CallScaffold(localData, navController).CreateScreenApp()
            }
            composable(Routes.Account.route) {
                CallScaffold(localData, navController).CreateScreenApp()
            }
        }
    }
}