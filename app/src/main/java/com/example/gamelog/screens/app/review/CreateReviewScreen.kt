package com.example.gamelog.screens.app.review

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReviewScreen(paddingValues: PaddingValues, createReviewViewModel: CreateReviewViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (createReviewViewModel.isLoadingGames) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = createReviewViewModel.selectedGame?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecione um Jogo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    createReviewViewModel.availableGames.forEach { game ->
                        DropdownMenuItem(
                            text = { Text(game.name ?: "Sem nome") },
                            onClick = {
                                createReviewViewModel.selectedGame = game
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Column {
            Text(
                text = "Nota: ${createReviewViewModel.rating.roundToInt()}/5",
                style = MaterialTheme.typography.bodyLarge
            )
            Slider(
                value = createReviewViewModel.rating,
                onValueChange = { createReviewViewModel.rating = it },
                valueRange = 0f..5f,
                steps = 4,
                modifier = Modifier.fillMaxWidth()
            )
        }

        OutlinedTextField(
            value = createReviewViewModel.title,
            onValueChange = { createReviewViewModel.title = it },
            label = { Text("Título da Review") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = createReviewViewModel.content,
            onValueChange = { createReviewViewModel.content = it },
            label = { Text("Conteúdo") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            maxLines = 8
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { createReviewViewModel.navController.popBackStack() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = { createReviewViewModel.saveReview() },
                modifier = Modifier.weight(1f),
                enabled = createReviewViewModel.selectedGame != null &&
                        createReviewViewModel.title.isNotBlank()
            ) {
                Text("Salvar")
            }
        }

    }
}