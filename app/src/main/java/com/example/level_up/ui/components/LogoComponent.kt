package com.example.level_up.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.level_up.R

/**
 * Componente reutilizable que muestra el logo de la aplicación.
 */
@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.logo_level_up),
        contentDescription = "Logo de Level-Up"
    )
}
