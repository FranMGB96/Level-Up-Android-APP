package com.example.level_up.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.AppDatabase
import com.example.level_up.model.Producto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {

    private val productoDao = AppDatabase.getDatabase(application).productoDao()

    // Expone la lista de todos los productos como un StateFlow.
    // La UI puede observar este flow para recibir actualizaciones automáticas.
    val productos = productoDao.obtenerTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * Guarda los cambios de un producto en la base de datos.
     * @param productoActualizado El objeto Producto con la información nueva.
     */
    fun guardarCambiosProducto(productoActualizado: Producto) {
        viewModelScope.launch {
            productoDao.actualizar(productoActualizado)
        }
    }
}
