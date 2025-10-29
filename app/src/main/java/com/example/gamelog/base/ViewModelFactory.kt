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
        val viewModel = createViewModelWithNavController(modelClass)
            ?: createViewModelWithoutNavController(modelClass)
            ?: createViewModelWithContext(modelClass)
            ?: throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")

        return viewModel as T
    }

    private fun <T : ViewModel> createViewModelWithNavController(modelClass: Class<T>): ViewModel? {
        return try {
            val constructor = modelClass.getConstructor(
                LocalData::class.java,
                NavController::class.java
            )
            constructor.newInstance(localData, requireNavController())
        } catch (e: NoSuchMethodException) {
            Log.d("ViewModelFactory", "Error creating ViewModel with NavController: ${e.message}")
            null
        }
    }

    private fun <T : ViewModel> createViewModelWithoutNavController(modelClass: Class<T>): ViewModel? {
        return try {
            val constructor = modelClass.getConstructor(LocalData::class.java)
            constructor.newInstance(localData)
        } catch (e: NoSuchMethodException) {
            Log.d("ViewModelFactory", "Error creating ViewModel without NavController: ${e.message}")
            null
        }
    }

    private fun <T : ViewModel> createViewModelWithContext(modelClass: Class<T>): ViewModel? {
        return try {
            val constructor = modelClass.getConstructor(
                LocalData::class.java,
                NavController::class.java,
                Context::class.java
            )
            constructor.newInstance(localData, requireNavController(), requireContext())
        } catch (e: NoSuchMethodException) {
            Log.d("ViewModelFactory", "Error creating ViewModel with Context: ${e.message}")
            null
        }
    }

    private fun requireNavController(): NavController {
        return navController ?: throw IllegalStateException("NavController is required")
    }

    private fun requireContext(): Context {
        return context ?: throw IllegalStateException("Context is required")
    }
}

