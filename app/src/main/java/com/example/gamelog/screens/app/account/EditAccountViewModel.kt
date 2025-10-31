package com.example.gamelog.screens.app.account

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest

class EditAccountViewModel(val navController: NavController, val applicationContext: Context) : ViewModel() {

    private val currentUser = Firebase.auth.currentUser

    var name by mutableStateOf(currentUser?.displayName ?: "")
    var email by mutableStateOf(currentUser?.email ?: "")
    var photoUrl by mutableStateOf(currentUser?.photoUrl?.toString())
    var isLoading by mutableStateOf(false)
    var message by mutableStateOf<String?>(null)
    var isError by mutableStateOf(false)

    val isGoogleUser = currentUser?.providerData?.any {
        it.providerId == GoogleAuthProvider.PROVIDER_ID
    } ?: false

    fun updateName(newName: String) {
        name = newName
    }

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    private fun updateFirebaseProfile() {
        val profileUpdates = userProfileChangeRequest {
            displayName = name
            photoUri = photoUrl?.toUri()
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