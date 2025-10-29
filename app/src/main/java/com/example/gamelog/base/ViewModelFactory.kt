package com.example.gamelog.base

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

class ViewModelFactory(
    private val navController: NavController? = null,
    private val context: Context? = null
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return createViewModel(modelClass) as T
    }

    private fun <T : ViewModel> createViewModel(modelClass: Class<T>): ViewModel {
        val constructorSignatures = listOf(
            // (NavController, Context)
            Triple(
                arrayOf(NavController::class.java, Context::class.java),
                arrayOf(navController, context),
                "NavController, Context"
            ),
            // (NavController)
            Triple(
                arrayOf(NavController::class.java),
                arrayOf(navController),
                "NavController"
            ),
            // (Context)
            Triple(
                arrayOf(Context::class.java),
                arrayOf(context),
                "Context"
            )
        )

        for ((paramTypes, paramValues, signature) in constructorSignatures) {
            if (paramValues.all { it != null }) {
                try {
                    val constructor = modelClass.getConstructor(*paramTypes)
                    return constructor.newInstance(*paramValues)
                } catch (e: NoSuchMethodException) {
                    Log.d("ViewModelFactory", "Constructor not found: $signature")
                }
            }
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}. " +
                    "No matching constructor found with available parameters."
        )
    }
}
