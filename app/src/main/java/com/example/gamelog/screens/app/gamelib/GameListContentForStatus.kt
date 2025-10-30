package com.example.gamelog.screens.app.gamelib

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gamelog.base.Routes
import com.example.gamelog.data.model.game.GameStatus
import com.example.gamelog.ui.components.GameDetailCardComponent

@Composable
fun GameListContentForStatus(
    gameLibViewModel: GameLibViewModel,
    status: GameStatus
) {
    val allGames by gameLibViewModel.allGames.collectAsState()
    val isLoading by gameLibViewModel.isLoading.collectAsState()

    val filteredGames = remember(allGames, status) {
        allGames.filter { it.status == status.value }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> Text("Carregando...")
            filteredGames.isEmpty() -> Text("Nenhum jogo encontrado")
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(
                        items = filteredGames,
                        key = { it.id ?: it.hashCode() }
                    ) { gameDetail ->
                        GameDetailCardComponent(
                            gameDetail = gameDetail,
                            onClick = {
                                gameDetail.id?.let { firebaseId ->
                                    gameLibViewModel.navController.navigate(
                                        Routes.GameDetail.editRoute(firebaseId.toString())
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

