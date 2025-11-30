package com.example.level_up.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * `AppButton` es un componente de botón reutilizable con el estilo de la aplicación.
 *
 * @param text El texto que se mostrará dentro del botón.
 * @param onClick La acción (lambda) que se ejecutará cuando el usuario presione el botón.
 * @param color El color de fondo del botón. Por defecto es verde brillante.
 * @param enabled Un booleano para activar o desactivar el botón. Por defecto es true.
 */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    color: Color = Color(0xFF00FF00),
    enabled: Boolean = true // Nuevo parámetro para controlar si el botón está activado.
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        // Usamos el nuevo parámetro "enabled" para controlar el estado del botón.
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            // Color que tendrá el botón cuando esté desactivado.
            disabledContainerColor = Color.DarkGray
        )
    ) {
        Text(text = text, color = Color.Black, fontSize = 16.sp)
    }
}
