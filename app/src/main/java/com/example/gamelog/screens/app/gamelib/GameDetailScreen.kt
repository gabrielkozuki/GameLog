package com.example.gamelog.screens.app.gamelib

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gamelog.data.util.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    paddingValues: PaddingValues,
    gameDetailViewModel: GameDetailViewModel,
    gameId: Int
) {
    Log.d("GameDetailScreen", "Recomposing with gameId=$gameId")

    val game by gameDetailViewModel.game.collectAsState()
    val isLoading by gameDetailViewModel.isLoading.collectAsState()
    val errorMessage by gameDetailViewModel.errorMessage.collectAsState()

    LaunchedEffect(gameId) {
        gameDetailViewModel.fetchGameDetail(gameId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = errorMessage ?: "",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )
                }
            }

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
                                model = gameDetail.background_image,
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
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
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
                            Box (
                                modifier = Modifier
                                    .padding(bottom = 24.dp)
                            ) {
                                Button(
                                    onClick = {
                                        gameDetailViewModel.addToLibrary(gameDetail)
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
                                                text = genre.name,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
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

                            if (gameDetail.description_raw != null) {
                                Text(
                                    text = gameDetail.description_raw
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
                                    .mapNotNull { it.platform.name }
                                    .take(3)
                                    .joinToString(", ")

                                DetailRow(
                                    label = "Plataformas",
                                    value = platformNames
                                )
                            }

                            if (!gameDetail.publishers.isNullOrEmpty()) {
                                val publisherNames =
                                    gameDetail.publishers.joinToString(", ") { it.name }

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