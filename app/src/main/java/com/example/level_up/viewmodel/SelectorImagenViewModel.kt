package com.example.level_up.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * ViewModel simple para gestionar el estado de una imagen seleccionada por el usuario.
 * Mantiene la URI de la imagen para que la UI pueda mostrarla.
 */
class SelectorImagenViewModel : ViewModel() {
    // Guarda la URI de la imagen seleccionada como un String. Es `null` si no se ha seleccionado ninguna.
    var uriImagen by mutableStateOf<String?>(null)
        private set

    /**
     * Actualiza la URI de la imagen. La UI llama a esta función cuando el usuario
     * ha terminado de seleccionar una imagen de la galería.
     */
    fun asignarUriImagen(uri: String?) {
        uriImagen = uri
    }
}
