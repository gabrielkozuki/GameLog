package com.example.gamelog.screens.app.gamelib

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamelog.data.model.GameStatus
import kotlinx.coroutines.launch
import kotlin.text.get

@Composable
fun GameLibScreen(paddingValues: PaddingValues, gameLibViewModel: GameLibViewModel) {

    val pages = arrayListOf(
        "Jogando",
        "Backlog",
        "Lista de Desejos",
        "Finalizado",
        "Abandonado",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(modifier = Modifier.padding(paddingValues)) {
            StatusTabCarousel(pages, paddingValues, gameLibViewModel)
        }

        FloatingActionButton(
            modifier = Modifier.padding(24.dp),
            onClick = {
                gameLibViewModel.addGame()
            }
        ) {
            Text(
                text = "+",
                fontSize = 15.sp
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatusTabCarousel(pages: List<String>, paddingValues: PaddingValues, gameLibViewModel: GameLibViewModel) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    val statusMap = mapOf(
        0 to GameStatus.JOGANDO,
        1 to GameStatus.BACKLOG,
        2 to GameStatus.LISTA_DE_DESEJOS,
        3 to GameStatus.FINALIZADO,
        4 to GameStatus.ABANDONADO
    )

    LaunchedEffect(Unit) {
        gameLibViewModel.loadAllGames()
    }

    Box(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(100))
            .padding(4.dp)
    ) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp,
            containerColor = Color.Transparent,
            indicator = { },
            divider = { }
        ) {
            pages.forEachIndexed { index, title ->
                val isSelected = pagerState.currentPage == index
                TabHeader(
                    title = title,
                    isSelected = isSelected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        key = { page -> statusMap[page]?.value ?: page }
    ) { page ->
        val status = statusMap[page] ?: GameStatus.JOGANDO

        GameListContentForStatus(
            gameLibViewModel = gameLibViewModel,
            status = status
        )
    }
}

@Composable
fun TabHeader(title: String, isSelected: Boolean, onClick: () -> Unit) {
    val textColor = if (isSelected) Color.Black else Color.White
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
    val backgroundModifier = if (isSelected) {
        Modifier.background(Color.White, shape = RoundedCornerShape(100))
    } else {
        Modifier
    }

    Tab(
        selected = isSelected,
        onClick = onClick,
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .then(backgroundModifier),
        text = {
            Text(
                text = title,
                color = textColor,
                fontWeight = fontWeight,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    )
}