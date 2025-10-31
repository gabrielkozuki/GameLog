package com.example.gamelog.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.gamelog.data.service.AppwriteConfig
import com.example.gamelog.ui.theme.GameLogTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseApp.initializeApp(this)
        AppwriteConfig.init(applicationContext)

        setContent {
            GameLogTheme {
                Navigation().Create()
            }
        }
    }
}