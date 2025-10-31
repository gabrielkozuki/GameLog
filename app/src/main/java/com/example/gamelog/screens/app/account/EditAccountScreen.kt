package com.example.gamelog.screens.app.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gamelog.R

@Composable
fun EditAccountScreen(paddingValues: PaddingValues, viewModel: EditAccountViewModel) {

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.uploadProfileImage(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable(enabled = !viewModel.isLoading && !viewModel.isGoogleUser) {
                        imagePickerLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.photoUrl != null) {
                    AsyncImage(
                        model = viewModel.photoUrl,
                        contentDescription = "Foto do perfil",
                        modifier = Modifier.fillMaxSize(),
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
            }

            if (!viewModel.isGoogleUser) {
                FloatingActionButton(
                    onClick = {
                        if (!viewModel.isLoading) {
                            imagePickerLauncher.launch("image/*")
                        }
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.BottomEnd),
                    containerColor = if (viewModel.isLoading)
                        MaterialTheme.colorScheme.surfaceVariant
                    else
                        MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.photo_camera_24),
                        contentDescription = "Alterar foto",
                        modifier = Modifier.size(20.dp),
                        tint = if (viewModel.isLoading)
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else
                            MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.updateName(it) },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !viewModel.isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !viewModel.isLoading && !viewModel.isGoogleUser
        )

        if (viewModel.isGoogleUser) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "E-mail e foto vinculados à conta Google não podem ser alterados",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.saveChanges() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !viewModel.isLoading
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Salvar Alterações")
            }
        }

        viewModel.message?.let { msg ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = msg,
                color = if (viewModel.isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
