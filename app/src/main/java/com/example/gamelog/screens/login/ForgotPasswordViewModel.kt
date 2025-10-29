package com.example.gamelog.screens.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.data.model.LocalData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ForgotPasswordViewModel(val localData: LocalData, val navController: NavController, val applicationContext: Context): ViewModel() {

    private var _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private var _emailError = MutableStateFlow("")
    val emailError: StateFlow<String> = _emailError

    private var _successMessage = MutableStateFlow(false)
    val successMessage: StateFlow<Boolean> = _successMessage

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setEmailError(value: String) {
        _emailError.value = value
    }

    fun recoverPassword() {
        try {
            Firebase.auth.sendPasswordResetEmail(_email.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _successMessage.value = true
                    } else {
                        Toast.makeText(
                            applicationContext.applicationContext,
                            "Erro ao enviar e-mail de redefinição de senha: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext.applicationContext,
                "Erro ao redefinir senha: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    fun goBack() {
        navController.popBackStack()
    }

}