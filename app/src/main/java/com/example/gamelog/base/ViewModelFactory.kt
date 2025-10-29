package com.example.gamelog.base

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.gamelog.data.model.LocalData

class ViewModelFactory(
    private val localData: LocalData,
    private val navController: NavController? = null,
    private val context: Context? = null
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return createViewModel(modelClass) as T
    }

    private fun <T : ViewModel> createViewModel(modelClass: Class<T>): ViewModel {
        val constructorSignatures = listOf(
            // (LocalData, NavController, Context)
            Triple(
                arrayOf(LocalData::class.java, NavController::class.java, Context::class.java),
                arrayOf(localData, navController, context),
                "LocalData, NavController, Context"
            ),
            // (LocalData, NavController)
            Triple(
                arrayOf(LocalData::class.java, NavController::class.java),
                arrayOf(localData, navController),
                "LocalData, NavController"
            ),
            // (LocalData, Context)
            Triple(
                arrayOf(LocalData::class.java, Context::class.java),
                arrayOf(localData, context),
                "LocalData, Context"
            ),
            // (LocalData)
            Triple(
                arrayOf(LocalData::class.java),
                arrayOf(localData),
                "LocalData"
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
