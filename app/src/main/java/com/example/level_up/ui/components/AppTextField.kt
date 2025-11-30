package com.example.level_up.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

/**
 * `AppTextField` es un campo de texto reutilizable con el estilo de la aplicación.
 *
 * Abstrae la configuración de estilo y comportamiento, como la ocultación de texto
 * para contraseñas. Se integra con el patrón ViewModel al recibir el valor actual (`value`)
 * y notificar los cambios a través de la lambda `onValueChange`.
 *
 * @param value El texto actual que se muestra en el campo.
 * @param onValueChange La acción que se ejecuta cada vez que el usuario escribe.
 * @param label El texto que se muestra como pista dentro del campo.
 * @param isPassword Si es `true`, el texto se ocultará con puntos.
 * @param isError Si es `true`, el campo se mostrará con estilo de error (borde rojo).
 */
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    isError: Boolean = false // Nuevo parámetro para gestionar el estado de error
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        isError = isError, // Se pasa el estado de error al componente de Material
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedIndicatorColor = Color(0xFF00FF00),
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Color(0xFF00FF00),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            // Cuando `isError` es true, OutlinedTextField usará estos colores para el error.
            errorIndicatorColor = Color.Red,
            errorLabelColor = Color.Red,
            errorCursorColor = Color.Red,
            errorContainerColor = Color.Transparent
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text)
    )
}
