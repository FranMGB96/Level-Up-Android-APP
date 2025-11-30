package com.example.level_up.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.level_up.model.Articulo
import com.example.level_up.model.Usuario

/**
 * Objeto singleton para mantener el estado global de la sesión de la app.
 * Estos datos no son persistentes y se reinician cada vez que la app se cierra por completo.
 */
object EstadoApp {
    // Mantiene al usuario que ha iniciado sesión. `mutableStateOf` asegura que la UI
    // que observe este valor se recomponga cuando cambie.
    val usuarioActual = mutableStateOf<Usuario?>(null)

    // Mantiene la lista de artículos en el carrito. `mutableStateListOf` es una lista
    // observable que notifica a la UI de cualquier cambio en su contenido.
    val carrito = mutableStateListOf<Articulo>()
}
