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
import com.example.gamelog.screens.app.gamelib.gamedetail.GameDetailMode
import com.example.gamelog.screens.app.gamelib.gamedetail.GameDetailViewModel
import com.example.gamelog.screens.app.review.EditReviewViewModel
import com.example.gamelog.screens.app.review.ReviewDetailViewModel
import com.example.gamelog.screens.app.review.ReviewViewModel

class Navigation {
    private lateinit var navController: NavHostController

    @Composable
    fun Create() {
        navController = rememberNavController()

        NavHost(navController = navController, startDestination = Routes.Login.route) {
            // login
            composable(Routes.Login.route) {
                CallScaffold(navController).CreateScreen("login")
            }
            composable(Routes.Register.route) {
                CallScaffold(navController).CreateScreen("register")
            }
            composable(Routes.ForgotPassword.route) {
                CallScaffold(navController).CreateScreen("forgotPassword")
            }

            // game
            composable(Routes.GameLib.route) {
                CallScaffold(navController).CreateScreenApp()
            }
            composable(Routes.Search.route) {
                CallScaffold(navController).CreateScreen("search")
            }
            composable(
                route = Routes.GameDetail.route,
                arguments = listOf(
                    navArgument("mode") { type = NavType.StringType },
                    navArgument("id") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val context = LocalContext.current
                val modeStr = backStackEntry.arguments?.getString("mode") ?: "create"
                val id = backStackEntry.arguments?.getString("id") ?: "0"

                val mode = when (modeStr) {
                    "create" -> GameDetailMode.Create(id.toIntOrNull() ?: 0)
                    "edit" -> GameDetailMode.Edit(id)
                    else -> GameDetailMode.Create(0)
                }

                val viewModel: GameDetailViewModel = viewModel(
                    factory = ViewModelFactory(navController, context.applicationContext)
                )

                CallScaffold(navController).CreateGameDetailScreen(
                    viewModel = viewModel,
                    mode = mode
                )
            }

            // list
            composable(Routes.GameList.route) {
                CallScaffold(navController).CreateScreenApp()
            }

            // review
            composable(Routes.Review.route) {
                CallScaffold(navController).CreateScreenApp()
            }

            composable(
                route = "review/{mode}/{id}",
                arguments = listOf(
                    navArgument("mode") { type = NavType.StringType },
                    navArgument("id") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                val mode = backStackEntry.arguments?.getString("mode") ?: "create"
                val reviewId = backStackEntry.arguments?.getString("id")?.takeIf { it.isNotEmpty() }

                CallScaffold(navController).CreateReviewScreen(mode, reviewId)
            }

            // account
            composable(Routes.Account.route) {
                CallScaffold(navController).CreateScreenApp()
            }
        }
    }
}