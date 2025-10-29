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
import com.example.gamelog.data.model.LocalData
import com.example.gamelog.screens.app.AccountScreen
import com.example.gamelog.screens.app.AccountViewModel
import com.example.gamelog.screens.app.gamelib.*
import com.example.gamelog.screens.app.gamelist.GameListScreen
import com.example.gamelog.screens.app.gamelist.GameListViewModel
import com.example.gamelog.screens.app.review.ReviewScreen
import com.example.gamelog.screens.app.review.ReviewViewModel
import com.example.gamelog.screens.login.*

class CallScaffold(private val localData: LocalData, val navController: NavController) {

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
                        factory = ViewModelFactory(localData, navController, context.applicationContext)
                    )
                    LoginScreen(paddingValues, viewModel)
                }
                Routes.Register.route -> {
                    val context = LocalContext.current
                    val viewModel: RegisterViewModel = viewModel(
                        factory = ViewModelFactory(localData, navController, context.applicationContext)
                    )
                    RegisterScreen(paddingValues, viewModel)
                }
                Routes.ForgotPassword.route -> {
                    val context = LocalContext.current
                    val viewModel: ForgotPasswordViewModel = viewModel(
                        factory = ViewModelFactory(localData, navController, context.applicationContext)
                    )
                    ForgotPasswordScreen(paddingValues, viewModel)
                }
                Routes.Search.route -> {
                    val viewModel: SearchViewModel = viewModel(
                        factory = ViewModelFactory(localData, navController)
                    )
                    SearchScreen(paddingValues, viewModel)
                }
            }
        }
        return PaddingValues()
    }

    @Composable
    fun CreateGameDetailScreen(
        viewModel: GameDetailViewModel,
        gameId: Int
    ) {
        Scaffold (
            topBar = {
                CreateCenterAlignedTopAppBar(string = "")
            }
        ) { innerPadding ->
            GameDetailScreen(innerPadding, viewModel, gameId)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CreateCenterAlignedTopAppBar(string: String, route: String? = null) {
        CenterAlignedTopAppBar(
            title = { Text(string) },
            navigationIcon = {
                IconButton(onClick = {
                    route?.let {
                        navController.popBackStack(
                            route = route,
                            inclusive = false
                        )
                    } ?: run {
                        navController.popBackStack()
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

        var selectedIndex by remember { mutableIntStateOf(0) }

        Scaffold (
            bottomBar = {
                NavigationBar {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
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
                        factory = ViewModelFactory(localData, navController)
                    )
                    GameLibScreen(paddingValues, viewModel)
                }
                1 -> {
                    val viewModel: GameListViewModel = viewModel(
                        factory = ViewModelFactory(localData, navController)
                    )
                    GameListScreen(paddingValues, viewModel)
                }
                2 -> {
                    val viewModel: ReviewViewModel = viewModel(
                        factory = ViewModelFactory(localData, navController)
                    )
                    ReviewScreen(paddingValues, viewModel)
                }
                3 -> {
                    val context = LocalContext.current
                    val viewModel: AccountViewModel = viewModel(
                        factory = ViewModelFactory(localData, navController, context.applicationContext)
                    )
                    AccountScreen(paddingValues, viewModel)
                }
            }
        }
        return PaddingValues()
    }
}
