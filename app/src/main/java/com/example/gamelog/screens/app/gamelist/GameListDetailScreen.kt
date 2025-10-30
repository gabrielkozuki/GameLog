package com.example.gamelog.screens.app.gamelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GameListDetailScreen(
    paddingValues: PaddingValues,
    viewModel: GameListDetailViewModel,
    gameListId: String
) {
    LaunchedEffect(gameListId) {
        viewModel.loadGameList(gameListId)
    }

    if (viewModel.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val gameList = viewModel.gameList

        if (gameList == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Jogatina não encontrada")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = gameList.title,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        if (gameList.description.isNotBlank()) {
                            Text(
                                text = gameList.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    IconButton(onClick = { viewModel.navigateToEdit() }) {
                        Icon(Icons.Default.Edit, "Editar")
                    }
                }

                Text(
                    text = "Jogos (${gameList.games.size})",
                    style = MaterialTheme.typography.titleMedium
                )

                if (gameList.games.isEmpty()) {
                    Text(
                        "Nenhum jogo adicionado",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        gameList.games.forEach { game ->
                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = game.gameTitle,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Tarefas (${viewModel.tasks.count { it.completed }}/${viewModel.tasks.size})",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = viewModel.newTaskTitle,
                        onValueChange = { viewModel.newTaskTitle = it },
                        label = { Text("Nova tarefa") },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { viewModel.addTask() },
                        enabled = viewModel.newTaskTitle.isNotBlank()
                    ) {
                        Icon(Icons.Default.Add, "Adicionar")
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    viewModel.tasks.forEach { task ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = task.completed,
                                        onCheckedChange = { viewModel.toggleTask(task.id) }
                                    )

                                    Text(
                                        text = task.title,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }

                                IconButton(onClick = { viewModel.deleteTask(task.id) }) {
                                    Icon(Icons.Default.Delete, "Excluir")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}