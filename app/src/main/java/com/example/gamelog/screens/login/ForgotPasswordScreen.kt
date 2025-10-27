package com.example.gamelog.screens.login

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
fun ForgotPasswordScreen(paddingValues: PaddingValues, forgotPasswordViewModel: ForgotPasswordViewModel) {

    val email by forgotPasswordViewModel.email.collectAsState()
    val emailError by forgotPasswordViewModel.emailError.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Será enviado um link no seu e-mail para redefinir sua senha.",
            fontSize = 14.sp,
            modifier = Modifier.padding(20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { forgotPasswordViewModel.setEmail(it) },
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

        Button(
            onClick = {
                forgotPasswordViewModel.setEmailError(if (email.isBlank()) "O email é obrigatório" else "")
                if (emailError.isEmpty()) {
                    forgotPasswordViewModel.recoverPassword()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(90.dp)
        ) {
            Text("Redefinir senha")
        }
    }
}