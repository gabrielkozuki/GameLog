package com.example.gamelog.screens.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.gamelog.R
import com.example.gamelog.base.Navigation
import com.example.gamelog.ui.theme.GameLogTheme

@Composable
fun RegisterScreen(paddingValues: PaddingValues, registerViewModel: RegisterViewModel) {

    val email by registerViewModel.email.collectAsState()
    val password by registerViewModel.password.collectAsState()
    val passwordVisible by registerViewModel.passwordVisible.collectAsState()
    val emailError by registerViewModel.emailError.collectAsState()
    val passwordError by registerViewModel.passwordError.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Preencha seus dados para realizar o cadastro no sistema.",
            fontSize = 14.sp,
            modifier = Modifier.padding(20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { registerViewModel.setEmail(it) },
            label = { Text(emailError.ifEmpty { "Email" }, color = if (emailError.isNotEmpty()) Red else Unspecified) },
            leadingIcon = {
                Icon(Icons.Rounded.AccountCircle, contentDescription = null)
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Transparent,
                unfocusedIndicatorColor = Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { registerViewModel.setPassword(it) },
            label = { Text(passwordError.ifEmpty { "Senha" }, color = if (passwordError.isNotEmpty()) Red else Unspecified) },
            leadingIcon = {
                Icon(Icons.Rounded.Lock, contentDescription = null)
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisible) painterResource(R.drawable.visibility_24)
                    else painterResource(R.drawable.visibility_off_24)

                Icon(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier.clickable { registerViewModel.setPasswordVisible(!passwordVisible) }
                )
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Transparent,
                unfocusedIndicatorColor = Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val isEmailBlank = email.isBlank()
                val isPasswordBlank = password.isBlank()

                registerViewModel.setEmailError(if (isEmailBlank) "O email é obrigatório" else "")
                registerViewModel.setPasswordError(if (isPasswordBlank) "A senha é obrigatória" else "")

                if (!isEmailBlank && !isPasswordBlank) {
                    registerViewModel.register()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(90.dp)
        ) {
            Text("Registrar-se")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    GameLogTheme {
        Navigation().Create()
    }
}
