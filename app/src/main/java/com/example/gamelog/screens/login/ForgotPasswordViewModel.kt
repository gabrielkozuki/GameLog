package com.example.gamelog.screens.login

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.data.model.LocalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ForgotPasswordViewModel(val localData: LocalData, val navController: NavController): ViewModel() {

    private var _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private var _emailError = MutableStateFlow("")
    val emailError: StateFlow<String> = _emailError

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setEmailError(value: String) {
        _emailError.value = value
    }

    fun recoverPassword() {
        // TODO: handle recover password logic
    }

    fun goBack() {
        navController.popBackStack()
    }

}