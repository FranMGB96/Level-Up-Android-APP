package com.example.level_up.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up.ui.components.Logo
import com.example.level_up.viewmodel.SessionState
import com.example.level_up.viewmodel.SplashViewModel

@Composable
fun PantallaScreen(
    onNavigateToLogin: () -> Unit, // Ruta si no hay sesión
    onNavigateToCatalog: () -> Unit // Ruta si hay sesión
) {
    val splashViewModel: SplashViewModel = viewModel()
    val sessionState by splashViewModel.sessionState.collectAsState()

    LaunchedEffect(sessionState) {
        when (sessionState) {
            SessionState.LOGGED_IN -> {
                // Hay una sesión, vamos al catálogo
                onNavigateToCatalog()
            }
            SessionState.LOGGED_OUT -> {
                // No hay sesión, vamos al login
                onNavigateToLogin()
            }
            SessionState.LOADING -> {
                // Sigue mostrando el logo mientras se comprueba
            }
        }
    }

    // Pantalla de carga con el logo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Logo()
    }
}
