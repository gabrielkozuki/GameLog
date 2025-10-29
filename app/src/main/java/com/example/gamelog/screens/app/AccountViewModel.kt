package com.example.gamelog.screens.app

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.BuildConfig
import com.example.gamelog.base.Routes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class AccountViewModel(val navController: NavController, val applicationContext: Context): ViewModel() {

    var showLogoutDialog by mutableStateOf(false)

    val currentUser = Firebase.auth.currentUser
    val userName: String = currentUser?.displayName ?: "Usuário"
    val userEmail: String = currentUser?.email ?: ""
    val userPhotoUrl: String? = currentUser?.photoUrl?.toString()

    fun changeShowLogoutDialog(value: Boolean) {
        showLogoutDialog = value
    }

    fun signOut() {
        Firebase.auth.signOut()

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
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
