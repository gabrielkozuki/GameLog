package com.example.gamelog.screens.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gamelog.screens.app.AccountViewModel

@Composable
fun AccountScreen(paddingValues: PaddingValues, accountViewModel: AccountViewModel) {

    Box(modifier = Modifier.padding(paddingValues)) {
        Text("Conta")
    }

}