package com.example.gamelog.screens.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.base.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(val navController: NavController, val applicationContext: Context): ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private var _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private var _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible

    private var _emailError = MutableStateFlow("")
    val emailError: StateFlow<String> = _emailError

    private var _passwordError = MutableStateFlow("")
    val passwordError: StateFlow<String> = _passwordError

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }

    fun setPasswordVisible(value: Boolean) {
        _passwordVisible.value = value
    }

    fun setEmailError(value: String) {
        _emailError.value = value
    }

    fun setPasswordError(value: String) {
        _passwordError.value = value
    }

    fun login() {
        val emailValue = email.value.trim()
        val passwordValue = password.value

        setEmailError("")
        setPasswordError("")

        auth.signInWithEmailAndPassword(emailValue, passwordValue)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate(Routes.GameLib.route) {
                        popUpTo(0) { inclusive = true }
                    }
                } else {
                    val message = task.exception?.localizedMessage ?: "Erro ao fazer login"

                    Toast.makeText(
                        applicationContext.applicationContext,
                        message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

    }

    fun launchGoogleSignIn(idToken: String?) {
        if (idToken == null) {
            Toast.makeText(
                applicationContext,
                "Erro ao obter token do Google",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Login com Google realizado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()

                    navController.navigate(Routes.GameLib.route) {
                        popUpTo(0) { inclusive = true }
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Erro ao realizar login com Google: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun register() {
        navController.navigate(Routes.Register.route)
    }

    fun forgotPassword() {
        navController.navigate(Routes.ForgotPassword.route)
    }
}