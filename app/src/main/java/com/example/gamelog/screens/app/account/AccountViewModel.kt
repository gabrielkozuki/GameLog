package com.example.gamelog.screens.app.account

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.base.Routes
import com.example.gamelog.data.util.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class AccountViewModel(val navController: NavController, val applicationContext: Context): ViewModel() {

    var showLogoutDialog by mutableStateOf(false)

    private val currentUser get() = Firebase.auth.currentUser

    var userName by mutableStateOf(currentUser?.displayName ?: "Usuário")
        private set

    var userEmail by mutableStateOf(currentUser?.email ?: "")
        private set

    var userPhotoUrl by mutableStateOf(currentUser?.photoUrl?.toString())
        private set

    fun refreshUserData() {
        userName = currentUser?.displayName ?: "Usuário"
        userEmail = currentUser?.email ?: ""
        userPhotoUrl = currentUser?.photoUrl?.toString()
    }

    fun changeShowLogoutDialog(value: Boolean) {
        showLogoutDialog = value
    }

    fun signOut() {
        Firebase.auth.signOut()

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Constants.GOOGLE_CLIENT_ID)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(applicationContext.applicationContext, googleSignInOptions)
        googleSignInClient.signOut().addOnCompleteListener {
            navController.navigate(Routes.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

}