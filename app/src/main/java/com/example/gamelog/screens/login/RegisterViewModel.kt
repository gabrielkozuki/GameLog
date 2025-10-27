package com.example.gamelog.screens.login

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gamelog.base.Routes
import com.example.gamelog.model.LocalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterViewModel(val localData: LocalData, val navController: NavController): ViewModel() {

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

    fun register() {
        // TODO: handle register logic
        navController.popBackStack()
    }

}