package com.example.level_up.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up.ui.components.AppButton
import com.example.level_up.ui.components.AppTextField
import com.example.level_up.ui.components.Logo
import com.example.level_up.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onNavigate: () -> Unit, // Se cambia el nombre del parámetro
    onRegister: () -> Unit
) {
    val loginViewModel: LoginViewModel = viewModel()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(loginViewModel.loginSuccess, lifecycleOwner) {
        loginViewModel.loginSuccess
            .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect { 
                onNavigate() // Se usa el nuevo nombre del parámetro
            }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()

            Spacer(Modifier.height(32.dp))

            AppTextField(
                value = loginViewModel.email,
                onValueChange = { loginViewModel.onEmailChange(it) },
                label = "Email",
                isError = loginViewModel.error != null
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = loginViewModel.password,
                onValueChange = { loginViewModel.onPasswordChange(it) },
                label = "Contraseña",
                isPassword = true,
                isError = loginViewModel.error != null
            )

            Spacer(Modifier.height(24.dp))

            AppButton(
                text = "INICIAR SESIÓN",
                onClick = { loginViewModel.onLoginClick() }
            )

            TextButton(onClick = onRegister) {
                Text("¿No tienes cuenta? Regístrate", color = Color(0xFF00FF00))
            }

            loginViewModel.error?.let {
                Text(it, color = Color.Red, modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}
