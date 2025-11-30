package com.example.level_up.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.AppDatabase
import com.example.level_up.data.EstadoApp
import com.example.level_up.model.Usuario
import kotlinx.coroutines.launch

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()

    // Mantiene una referencia al usuario actual. Observa los cambios desde EstadoApp.
    val usuario: Usuario? = EstadoApp.usuarioActual.value

    // Estado para el campo de texto del nombre, inicializado con el nombre actual.
    var nombre by mutableStateOf(usuario?.nombre ?: "")
        private set

    // Estado para mostrar un mensaje de éxito.
    var guardadoConExito by mutableStateOf(false)
        private set

    /**
     * Se llama cuando el usuario cambia el texto en el campo del nombre.
     */
    fun onNombreChange(nuevoNombre: String) {
        nombre = nuevoNombre
        guardadoConExito = false // Oculta el mensaje si se empieza a editar de nuevo
    }

    /**
     * Guarda los cambios en la base de datos.
     */
    fun guardarCambios() {
        // Asegurarse de que el usuario no sea nulo y el nombre no esté vacío
        if (usuario != null && nombre.isNotBlank()) {
            viewModelScope.launch {
                // Crear una copia del usuario con el nuevo nombre
                val usuarioActualizado = usuario.copy(nombre = nombre.trim())

                // Actualizar la base de datos
                usuarioDao.actualizar(usuarioActualizado)

                // Actualizar el estado global de la app
                EstadoApp.usuarioActual.value = usuarioActualizado

                // Indicar que se guardó con éxito
                guardadoConExito = true
            }
        }
    }
}
