package com.example.gamelog.screens.app.gamelib

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamelog.R
import com.example.gamelog.ui.components.GameCardComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(paddingValues: PaddingValues, searchViewModel: SearchViewModel) {

    val search by searchViewModel.search.collectAsState()
    val games by searchViewModel.games.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()
    val errorMessage by searchViewModel.errorMessage.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = search,
                onValueChange = { searchViewModel.setSearch(it) },
                label = { Text("Digite o nome do jogo") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Transparent,
                    unfocusedIndicatorColor = Transparent
                ),
                singleLine = true
            )

            Button(
                onClick = {
                    searchViewModel.search()
                },
            ) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        }

        when {
            isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = errorMessage ?: "",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )
                }
            }

            games.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(games) { game ->
                        GameCardComponent(game, onClick = {
                            searchViewModel.gameDetail(game.id)
                        })
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }

            else -> {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(ImageVector.vectorResource(id = R.drawable.gamepad_24), contentDescription = null, Modifier.size(80.dp), tint = Gray)

                    Text(
                        text = "Pesquise um jogo para adicioná-lo à sua biblioteca!",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp)
                    )
                }
            }
        }
    }
}