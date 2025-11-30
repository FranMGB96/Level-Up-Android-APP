package com.example.level_up.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up.ui.components.AppButton
import com.example.level_up.ui.components.AppTextField
import com.example.level_up.ui.components.Logo
import com.example.level_up.viewmodel.RegistrarViewModel
import kotlinx.coroutines.delay

@Composable
fun RegistrarScreen(
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val registrarViewModel: RegistrarViewModel = viewModel()
    val uiState by registrarViewModel.uiState.collectAsState()

    if (uiState.registroExitoso) {
        LaunchedEffect(Unit) {
            delay(2000)
            onSuccess()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.registroExitoso) {
            // Vista de éxito
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Logo()
                Spacer(Modifier.height(32.dp))
                Text(
                    text = "¡Gracias por registrarte, ${uiState.nombre}!",
                    color = Color(0xFF00FF00),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Serás redirigido al catálogo...",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        } else {
            // Vista del formulario
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .verticalScroll(rememberScrollState()), // Para que no se corte en pantallas pequeñas
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Logo()

                Spacer(Modifier.height(32.dp))

                // --- Campo Nombre ---
                AppTextField(
                    value = uiState.nombre,
                    onValueChange = { registrarViewModel.onNombreChange(it) },
                    label = "Nombre",
                    isError = uiState.errorNombre != null
                )
                uiState.errorNombre?.let { error ->
                    Text(error, color = Color.Red, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
                }

                Spacer(Modifier.height(16.dp))

                // --- Campo Email ---
                AppTextField(
                    value = uiState.email,
                    onValueChange = { registrarViewModel.onEmailChange(it) },
                    label = "Email",
                    isError = uiState.errorEmail != null
                )
                uiState.errorEmail?.let { error ->
                    Text(error, color = Color.Red, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
                }

                Spacer(Modifier.height(16.dp))

                // --- Campo Contraseña ---
                AppTextField(
                    value = uiState.password,
                    onValueChange = { registrarViewModel.onPasswordChange(it) },
                    label = "Contraseña",
                    isPassword = true,
                    isError = uiState.errorPassword != null
                )
                uiState.errorPassword?.let { error ->
                    Text(error, color = Color.Red, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
                }

                Spacer(Modifier.height(16.dp))

                // --- Campo Confirmar Contraseña ---
                AppTextField(
                    value = uiState.confirmarPassword,
                    onValueChange = { registrarViewModel.onConfirmarPasswordChange(it) },
                    label = "Confirmar Contraseña",
                    isPassword = true,
                    isError = uiState.errorConfirmarPassword != null
                )
                uiState.errorConfirmarPassword?.let { error ->
                    Text(error, color = Color.Red, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
                }

                // --- Error General ---
                uiState.errorGeneral?.let {
                    Spacer(Modifier.height(16.dp))
                    Text(it, color = Color.Red)
                }

                Spacer(Modifier.height(24.dp))

                AppButton(
                    text = "REGISTRARSE",
                    onClick = { registrarViewModel.onRegisterClick() }
                )

                TextButton(onClick = onBack) {
                    Text("¿Ya tienes cuenta? Inicia sesión", color = Color(0xFF00FF00))
                }
            }
        }
    }
}
