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
fun EditReviewScreen(
    paddingValues: PaddingValues,
    editReviewViewModel: EditReviewViewModel,
    reviewId: String
) {

    LaunchedEffect(reviewId) {
        editReviewViewModel.loadReview(reviewId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Editar Review",
            style = MaterialTheme.typography.headlineMedium
        )

        if (editReviewViewModel.isLoadingGames) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = editReviewViewModel.selectedGame?.name ?: "",
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
                    editReviewViewModel.availableGames.forEach { game ->
                        DropdownMenuItem(
                            text = { Text(game.name ?: "Sem nome") },
                            onClick = {
                                editReviewViewModel.selectedGame = game
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        // Rating Slider
        Column {
            Text(
                text = "Nota: ${editReviewViewModel.rating.roundToInt()}/5",
                style = MaterialTheme.typography.bodyLarge
            )
            Slider(
                value = editReviewViewModel.rating,
                onValueChange = { editReviewViewModel.rating = it },
                valueRange = 0f..5f,
                steps = 4,
                modifier = Modifier.fillMaxWidth()
            )
        }

        OutlinedTextField(
            value = editReviewViewModel.title,
            onValueChange = { editReviewViewModel.title = it },
            label = { Text("Título da Review") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = editReviewViewModel.content,
            onValueChange = { editReviewViewModel.content = it },
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
            OutlinedButton(
                onClick = { editReviewViewModel.showDeleteConfirmation() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Excluir")
            }

            OutlinedButton(
                onClick = { editReviewViewModel.navController.popBackStack() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = { editReviewViewModel.updateReview() },
                modifier = Modifier.weight(1f),
                enabled = editReviewViewModel.selectedGame != null &&
                        editReviewViewModel.title.isNotBlank()
            ) {
                Text("Atualizar")
            }
        }
    }

    if (editReviewViewModel.showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { editReviewViewModel.dismissDeleteDialog() },
            title = { Text("Confirmar Exclusão") },
            text = { Text("Tem certeza que deseja excluir esta review? Esta ação não pode ser desfeita.") },
            confirmButton = {
                Button(
                    onClick = { editReviewViewModel.confirmDelete() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Excluir")
                }
            },
            dismissButton = {
                TextButton(onClick = { editReviewViewModel.dismissDeleteDialog() }) {
                    Text("Cancelar")
                }
            }
        )
    }

}
