package com.example.gamelog.screens.app.gamelib.gamedetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gamelog.data.model.GameDetail
import com.example.gamelog.data.model.GameStatus
import com.example.gamelog.data.util.formatDate
import com.example.gamelog.ui.components.GameStatusSelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    paddingValues: PaddingValues,
    gameDetailViewModel: GameDetailViewModel,
    mode: GameDetailMode
) {
    val game by gameDetailViewModel.game.collectAsState()
    val isLoading by gameDetailViewModel.isLoading.collectAsState()
    val currentMode by gameDetailViewModel.mode.collectAsState()

    LaunchedEffect(mode) {
        gameDetailViewModel.initializeScreen(mode)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        when {
            isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            game != null -> {
                game?.let { gameDetail ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(paddingValues.calculateTopPadding())
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .background(Color.Black)
                        ) {
                            AsyncImage(
                                model = gameDetail.backgroundImage,
                                contentDescription = gameDetail.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.Center),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .align(Alignment.TopStart)
                            )

                            Surface(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp),
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFF2A2A2A)
                            ) {
                                Text(
                                    text = "${gameDetail.name} • ${gameDetail.released?.take(4) ?: ""}",
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ),
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            GameDetailActions(mode, gameDetail, gameDetailViewModel)

                            if (!gameDetail.genres.isNullOrEmpty()) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.padding(bottom = 16.dp)
                                ) {
                                    gameDetail.genres.take(3).forEach { genre ->
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = Color(0xFFE0E0E0)
                                        ) {
                                            Text(
                                                text = genre.name ?: "",
                                                modifier = Modifier.padding(
                                                    horizontal = 12.dp,
                                                    vertical = 6.dp
                                                ),
                                                fontSize = 12.sp,
                                                color = Color(0xFF333333)
                                            )
                                        }
                                    }
                                }
                            }

                            Text(
                                text = "Sinopse",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            if (gameDetail.descriptionRaw != null) {
                                Text(
                                    text = gameDetail.descriptionRaw
                                        .replace("\n\n", "\n")
                                        .replace("\n", " ").trim(),
                                    fontSize = 14.sp,
                                    color = Color(0xFF666666),
                                    lineHeight = 20.sp,
                                    textAlign = TextAlign.Justify,
                                    modifier = Modifier
                                        .padding(bottom = 24.dp)
                                )
                            }

                            Text(
                                text = "Detalhes",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            gameDetail.rating.let { rating ->
                                DetailRow(
                                    label = "Avaliação",
                                    value = String.format("%.1f/5", rating)
                                )
                            }

                            gameDetail.released?.let { released ->
                                DetailRow(
                                    label = "Lançamento",
                                    value = released.formatDate()
                                )
                            }

                            gameDetail.metacritic?.let { meta ->
                                DetailRow(
                                    label = "Metacritic",
                                    value = meta.toString()
                                )
                            }

                            if (!gameDetail.platforms.isNullOrEmpty()) {
                                val platformNames = gameDetail.platforms
                                    .mapNotNull { it.platform?.name }
                                    .take(3)
                                    .joinToString(", ")

                                DetailRow(
                                    label = "Plataformas",
                                    value = platformNames
                                )
                            }

                            if (!gameDetail.publishers.isNullOrEmpty()) {
                                val publisherNames =
                                    gameDetail.publishers.joinToString(", ") { it.name ?: "" }

                                DetailRow(
                                    label = "Publicadoras",
                                    value = publisherNames
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GameDetailActions(
    mode: GameDetailMode,
    gameDetail: GameDetail,
    viewModel: GameDetailViewModel
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(GameStatus.fromValue(gameDetail.status ?: 2) ?: GameStatus.BACKLOG) }

    Box(
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        when (mode) {
            is GameDetailMode.Create -> {
                Button(
                    onClick = {
                        viewModel.addToLibrary(gameDetail)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar à biblioteca",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Adicionar à biblioteca",
                        fontSize = 16.sp
                    )
                }
            }

            is GameDetailMode.Edit -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Status",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        GameStatusSelector (
                            selectedStatus = selectedStatus,
                            onStatusSelected = { status ->
                                selectedStatus = status
                            }
                        )
                    }

                    Button(
                        onClick = {
                            val updatedGame = gameDetail.copy(status = selectedStatus.value)
                            viewModel.updateGame(updatedGame)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Salvar Alterações",
                            fontSize = 16.sp
                        )
                    }

                    OutlinedButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFDC3545)
                        ),
                        border = BorderStroke(1.dp, Color(0xFFDC3545))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remover",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Remover da Biblioteca",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Remover jogo?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Tem certeza que deseja remover \"${gameDetail.name}\" da sua biblioteca? Esta ação não pode ser desfeita."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        gameDetail.id?.let { id ->
                            viewModel.deleteGame(id)
                        }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDC3545)
                    )
                ) {
                    Text("Remover")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF999999)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color(0xFF333333),
            fontWeight = FontWeight.Medium
        )
    }
}