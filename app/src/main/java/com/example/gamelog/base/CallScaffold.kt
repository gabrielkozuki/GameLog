package com.example.gamelog.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gamelog.R
import com.example.gamelog.screens.app.AccountScreen
import com.example.gamelog.screens.app.AccountViewModel
import com.example.gamelog.screens.app.gamelib.*
import com.example.gamelog.screens.app.gamelib.gamedetail.GameDetailMode
import com.example.gamelog.screens.app.gamelib.gamedetail.GameDetailScreen
import com.example.gamelog.screens.app.gamelib.gamedetail.GameDetailViewModel
import com.example.gamelog.screens.app.gamelist.CreateGameListScreen
import com.example.gamelog.screens.app.gamelist.CreateGameListViewModel
import com.example.gamelog.screens.app.gamelist.EditGameListScreen
import com.example.gamelog.screens.app.gamelist.EditGameListViewModel
import com.example.gamelog.screens.app.gamelist.GameListDetailScreen
import com.example.gamelog.screens.app.gamelist.GameListDetailViewModel
import com.example.gamelog.screens.app.gamelist.GameListScreen
import com.example.gamelog.screens.app.gamelist.GameListViewModel
import com.example.gamelog.screens.app.review.CreateReviewScreen
import com.example.gamelog.screens.app.review.CreateReviewViewModel
import com.example.gamelog.screens.app.review.EditReviewScreen
import com.example.gamelog.screens.app.review.EditReviewViewModel
import com.example.gamelog.screens.app.review.ReviewDetailScreen
import com.example.gamelog.screens.app.review.ReviewDetailViewModel
import com.example.gamelog.screens.app.review.ReviewScreen
import com.example.gamelog.screens.app.review.ReviewViewModel
import com.example.gamelog.screens.login.*

class CallScaffold(val navController: NavController) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateScreen(screen: String): PaddingValues {
        Scaffold (
            topBar = {
                when (screen) {
                    Routes.Register.route -> CreateCenterAlignedTopAppBar("Registrar")
                    Routes.ForgotPassword.route -> CreateCenterAlignedTopAppBar("Esqueceu a senha")
                    Routes.Search.route -> CreateCenterAlignedTopAppBar("Procurar por um jogo")
                }
            }
        ) { paddingValues ->
            when(screen) {
                Routes.Login.route -> {
                    val context = LocalContext.current
                    val viewModel: LoginViewModel = viewModel(
                        factory = ViewModelFactory( navController, context.applicationContext)
                    )
                    LoginScreen(paddingValues, viewModel)
                }
                Routes.Register.route -> {
                    val context = LocalContext.current
                    val viewModel: RegisterViewModel = viewModel(
                        factory = ViewModelFactory( navController, context.applicationContext)
                    )
                    RegisterScreen(paddingValues, viewModel)
                }
                Routes.ForgotPassword.route -> {
                    val context = LocalContext.current
                    val viewModel: ForgotPasswordViewModel = viewModel(
                        factory = ViewModelFactory( navController, context.applicationContext)
                    )
                    ForgotPasswordScreen(paddingValues, viewModel)
                }
                Routes.Search.route -> {
                    val viewModel: SearchViewModel = viewModel(
                        factory = ViewModelFactory( navController)
                    )
                    SearchScreen(paddingValues, viewModel)
                }
            }
        }
        return PaddingValues()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CreateCenterAlignedTopAppBar(string: String, route: String? = null) {
        CenterAlignedTopAppBar(
            title = { Text(string) },
            navigationIcon = {
                IconButton(onClick = {
                    if (route != null) {
                        navController.navigate(route) {
                            popUpTo(route) { inclusive = false }
                        }
                    } else {
                        if (!navController.popBackStack()) {
                            navController.navigate(Routes.GameLib.route)
                        }
                    }
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }
            }

        )
    }

    @Composable
    fun CreateScreenApp(): PaddingValues {
        val navItemList = listOf(
            NavItem(label = "Biblioteca", icon = ImageVector.vectorResource(id = R.drawable.joystick_24)),
            NavItem(label = "Jogatinas", icon = Icons.AutoMirrored.Filled.List),
            NavItem(label = "Reviews", icon = Icons.Default.Star),
            NavItem(label = "Conta", icon = Icons.Default.Person),
        )

        val currentRoute = navController.currentBackStackEntryFlow
            .collectAsState(initial = navController.currentBackStackEntry)
            .value?.destination?.route

        val selectedIndex = when {
            currentRoute == Routes.GameLib.route -> 0
            currentRoute?.startsWith("gameList") == true -> 1
            currentRoute?.startsWith("review") == true -> 2
            currentRoute == Routes.Account.route -> 3
            else -> -1
        }

        Scaffold (
            bottomBar = {
                NavigationBar {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                val route = when (index) {
                                    0 -> Routes.GameLib.route
                                    1 -> Routes.GameList.route
                                    2 -> Routes.Review.route
                                    3 -> Routes.Account.route
                                    else -> Routes.GameLib.route
                                }

                                if (currentRoute != route) {
                                    navController.navigate(route) {
                                        popUpTo(Routes.GameLib.route) { inclusive = false }
                                        launchSingleTop = true
                                    }
                                }
                            },
                            icon = { Icon(navItem.icon, contentDescription = navItem.label) },
                            label = { Text(text = navItem.label, textAlign = TextAlign.Center) }
                        )
                    }
                }
            }
        ) { paddingValues ->
            when(selectedIndex) {
                0 -> {
                    val viewModel: GameLibViewModel = viewModel(
                        factory = ViewModelFactory(navController)
                    )
                    GameLibScreen(paddingValues, viewModel)
                }
                1 -> {
                    val viewModel: GameListViewModel = viewModel(
                        factory = ViewModelFactory(navController)
                    )
                    GameListScreen(paddingValues, viewModel)
                }
                2 -> {
                    val viewModel: ReviewViewModel = viewModel(
                        factory = ViewModelFactory(navController)
                    )
                    ReviewScreen(paddingValues, viewModel)
                }
                3 -> {
                    val context = LocalContext.current
                    val viewModel: AccountViewModel = viewModel(
                        factory = ViewModelFactory(navController, context.applicationContext)
                    )
                    AccountScreen(paddingValues, viewModel)
                }
            }
        }
        return PaddingValues()
    }

    // custom scaffolds
    @Composable
    fun CreateGameDetailScreen(
        viewModel: GameDetailViewModel,
        mode: GameDetailMode
    ) {
        Scaffold (
            topBar = {
                CreateCenterAlignedTopAppBar(string = "")
            }
        ) { innerPadding ->
            GameDetailScreen(innerPadding, viewModel, mode)
        }
    }

    @Composable
    fun CreateReviewScreen(mode: String, reviewId: String?) {
        Scaffold(
            topBar = {
                val title = when (mode) {
                    "create" -> "Criar Review"
                    "edit" -> "Editar Review"
                    "detail" -> "Detalhes do Review"
                    else -> "Review"
                }
                CreateCenterAlignedTopAppBar(string = title)
            }
        ) { innerPadding ->
            when (mode) {
                "create" -> {
                    val viewModel: CreateReviewViewModel = viewModel(
                        factory = ViewModelFactory(navController)
                    )
                    CreateReviewScreen(innerPadding, viewModel)
                }
                "edit" -> {
                    val viewModel: EditReviewViewModel = viewModel(
                        factory = ViewModelFactory(navController)
                    )
                    EditReviewScreen(innerPadding, viewModel, reviewId ?: "")
                }
                "detail" -> {
                    val viewModel: ReviewDetailViewModel = viewModel(
                        factory = ViewModelFactory(navController)
                    )
                    ReviewDetailScreen(innerPadding, viewModel, reviewId ?: "")
                }
            }
        }
    }

    @Composable
    fun CreateGameListScreen(mode: String, gamelistId: String?) {
        Scaffold(
            topBar = {
                val title = when (mode) {
                    "create" -> "Criar Jogatina"
                    "edit" -> "Editar Jogatina"
                    "detail" -> "Detalhes da Jogatina"
                    else -> "Review"
                }
                CreateCenterAlignedTopAppBar(string = title)
            }
        ) { innerPadding ->
            when (mode) {
                "create" -> {
                    val viewModel: CreateGameListViewModel = viewModel(
                        factory = ViewModelFactory(navController)
                    )
                    CreateGameListScreen(innerPadding, viewModel)
                }
                "edit" -> {
                    val viewModel: EditGameListViewModel = viewModel(
                        factory = ViewModelFactory(navController)
                    )
                    EditGameListScreen(innerPadding, viewModel, gamelistId ?: "")
                }
                "detail" -> {
                    val viewModel: GameListDetailViewModel = viewModel(
                        factory = ViewModelFactory(navController)
                    )
                    GameListDetailScreen(innerPadding, viewModel, gamelistId ?: "")
                }
            }
        }
    }

}
