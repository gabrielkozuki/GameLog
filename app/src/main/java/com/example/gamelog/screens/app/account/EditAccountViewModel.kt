package com.example.gamelog.screens.app.account

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.gamelog.data.service.ImageUploadService
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.launch

class EditAccountViewModel(val navController: NavController, val applicationContext: Context) : ViewModel() {

    private val currentUser = Firebase.auth.currentUser
    private val imageUploadService = ImageUploadService()

    var name by mutableStateOf(currentUser?.displayName ?: "")
    var email by mutableStateOf(currentUser?.email ?: "")
    var photoUrl by mutableStateOf(getInitialPhotoUrl())
    var isLoading by mutableStateOf(false)
    var message by mutableStateOf<String?>(null)
    var isError by mutableStateOf(false)

    val isGoogleUser = currentUser?.providerData?.any {
        it.providerId == GoogleAuthProvider.PROVIDER_ID
    } ?: false

    private fun getInitialPhotoUrl(): String? {
        val googlePhotoUrl = currentUser?.providerData
            ?.find { it.providerId == GoogleAuthProvider.PROVIDER_ID }
            ?.photoUrl
            ?.toString()
            ?.replace("s96-c", "s400-c")

        return googlePhotoUrl ?: currentUser?.photoUrl?.toString()
    }

    fun updateName(newName: String) {
        name = newName
    }

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    fun uploadProfileImage(imageUri: Uri) {
        if (isGoogleUser) {
            message = "Usuários do Google não podem alterar a foto de perfil através do app. Use sua conta Google."
            isError = true
            return
        }

        viewModelScope.launch {
            isLoading = true
            message = "Fazendo upload da imagem..."
            isError = false

            try {
                val imageUrl = imageUploadService.uploadProfileImage(
                    userId = currentUser?.uid ?: "",
                    imageUri = imageUri,
                    context = applicationContext
                )

                photoUrl = imageUrl
                message = "Imagem enviada com sucesso!"
                isError = false
            } catch (e: Exception) {
                val errorMsg = when {
                    e.message?.contains("bucket") == true -> "Erro: Bucket do Appwrite não configurado"
                    e.message?.contains("permission") == true -> "Erro: Sem permissão para fazer upload"
                    e.message?.contains("network") == true -> "Erro: Verifique sua conexão"
                    else -> "Erro: ${e.message ?: "Desconhecido"}"
                }
                message = errorMsg
                isError = true
                Log.e("EditAccount", "Erro no upload: ${e.message}", e)
            }

            isLoading = false
        }
    }

    private fun updateFirebaseProfile() {
        val profileUpdates = userProfileChangeRequest {
            displayName = name
            if (!isGoogleUser) {
                photoUri = photoUrl?.toUri()
            }
        }

        currentUser?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { profileTask ->
                if (profileTask.isSuccessful) {
                    if (!isGoogleUser && email != currentUser.email) {
                        currentUser.verifyBeforeUpdateEmail(email)
                            .addOnCompleteListener { emailTask ->
                                isLoading = false
                                if (emailTask.isSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Perfil atualizado! Verifique seu e-mail para confirmar a alteração.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.popBackStack()
                                } else {
                                    message = emailTask.exception?.message ?: "Erro ao atualizar e-mail"
                                    isError = true
                                }
                            }
                    } else {
                        isLoading = false
                        val toastMessage = if (isGoogleUser && email != currentUser.email) {
                            "Perfil atualizado! O e-mail não pode ser alterado em contas Google."
                        } else {
                            "Perfil atualizado com sucesso!"
                        }
                        Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_LONG).show()
                        navController.popBackStack()
                    }
                } else {
                    isLoading = false
                    message = profileTask.exception?.message ?: "Erro ao atualizar perfil"
                    isError = true
                }
            }
    }

    fun saveChanges() {
        if (name.isBlank()) {
            message = "O nome não pode estar vazio"
            isError = true
            return
        }

        if (email.isBlank()) {
            message = "O e-mail não pode estar vazio"
            isError = true
            return
        }

        isLoading = true
        message = null

        updateFirebaseProfile()
    }
}