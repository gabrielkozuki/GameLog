package com.example.gamelog.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.gamelog.R
import com.example.gamelog.model.LocalData
import com.example.gamelog.screens.app.AccountScreen
import com.example.gamelog.screens.app.AccountViewModel
import com.example.gamelog.screens.app.gamelib.GameLibScreen
import com.example.gamelog.screens.app.gamelib.GameLibViewModel
import com.example.gamelog.screens.app.gamelist.GameListScreen
import com.example.gamelog.screens.app.gamelist.GameListViewModel
import com.example.gamelog.screens.app.review.ReviewScreen
import com.example.gamelog.screens.app.review.ReviewViewModel
import com.example.gamelog.screens.login.ForgotPasswordScreen
import com.example.gamelog.screens.login.ForgotPasswordViewModel
import com.example.gamelog.screens.login.LoginScreen
import com.example.gamelog.screens.login.LoginViewModel
import com.example.gamelog.screens.login.RegisterScreen
import com.example.gamelog.screens.login.RegisterViewModel

class CallScaffold(localData: LocalData, val navController: NavController) {

    val loginViewModel = LoginViewModel(localData, navController)
    val registerViewModel = RegisterViewModel(localData, navController)
    val forgotPasswordViewModel = ForgotPasswordViewModel(localData, navController)

    val gameLibViewModel = GameLibViewModel(localData, navController)
    val gameListViewModel = GameListViewModel(localData, navController)
    val reviewViewModel = ReviewViewModel(localData, navController)
    val accountViewModel = AccountViewModel(localData, navController)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateScreenLogin(screen: String): PaddingValues {
        Scaffold (
            topBar = {
                when (screen) {
                    Routes.Register.route -> {
                        CenterAlignedTopAppBar(
                            title = {
                                Text("Registrar")
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    navController.popBackStack()
                                }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                                }
                            }
                        )
                    }
                    Routes.ForgotPassword.route -> {
                        CenterAlignedTopAppBar(
                            title = {
                                Text("Esqueceu a senha")
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    navController.popBackStack()
                                }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                                }
                            }
                        )
                    }
                }
            }
        ) { paddingValues ->
            when(screen) {
                Routes.Login.route -> {
                    LoginScreen(paddingValues, loginViewModel)
                }
                Routes.Register.route -> {
                    RegisterScreen(paddingValues, registerViewModel)
                }
                Routes.ForgotPassword.route -> {
                    ForgotPasswordScreen(paddingValues, forgotPasswordViewModel)
                }
            }
        }
        return PaddingValues()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateScreenApp(screen: String): PaddingValues {

        val navItemList = listOf(
            NavItem(label = "Biblioteca", icon = ImageVector.vectorResource(id = R.drawable.joystick_24)),
            NavItem(label = "Jogatinas", icon = Icons.AutoMirrored.Filled.List),
            NavItem(label = "Reviews", icon = Icons.Default.Star),
            NavItem(label = "Conta", icon = Icons.Default.Person),
        )

        var selectedIndex by remember {
            mutableIntStateOf(0)
        }

        Scaffold (
            bottomBar = {
                NavigationBar {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                            },
                            icon = {
                                Icon(navItem.icon, contentDescription = navItem.label)
                            },
                            label = {
                                Text(
                                    text = navItem.label,
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }
                }
            }
        ) { paddingValues ->
            when(selectedIndex) {
                0 -> {
                    GameLibScreen(paddingValues, gameLibViewModel)
                }
                1 -> {
                    GameListScreen(paddingValues, gameListViewModel)
                }
                2 -> {
                    ReviewScreen(paddingValues, reviewViewModel)
                }
                3 -> {
                    AccountScreen(paddingValues, accountViewModel)
                }
            }
        }
        return PaddingValues()
    }
}