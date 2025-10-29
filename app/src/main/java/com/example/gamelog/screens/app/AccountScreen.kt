package com.example.gamelog.screens.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun AccountScreen(paddingValues: PaddingValues, accountViewModel: AccountViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (accountViewModel.userPhotoUrl != null) {
                    AsyncImage(
                        model = accountViewModel.userPhotoUrl,
                        contentDescription = "Foto do usuário",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = accountViewModel.userName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = accountViewModel.userEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // TODO: profile picture upload

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { accountViewModel.changeShowLogoutDialog(true) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "Fazer Logout")
        }

    }

    if (accountViewModel.showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { accountViewModel.changeShowLogoutDialog(false) },
            title = { Text(text = "Confirmar Logout") },
            text = { Text(text = "Tem certeza que deseja sair?") },
            confirmButton = {
                TextButton(onClick = {
                    accountViewModel.changeShowLogoutDialog(false)
                    accountViewModel.signOut()
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = { accountViewModel.changeShowLogoutDialog(false) }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
