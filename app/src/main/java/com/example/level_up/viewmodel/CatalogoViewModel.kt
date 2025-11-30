package com.example.level_up.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.AppDatabase
import com.example.level_up.data.EstadoApp
import com.example.level_up.model.Articulo
import com.example.level_up.model.Producto
import com.example.level_up.repository.ProductoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class CatalogoViewModel(application: Application) : AndroidViewModel(application) {

    // 1. Inicializamos el repositorio, que ahora es más simple.
    private val repository: ProductoRepository = ProductoRepository(
        productoDao = AppDatabase.getDatabase(application).productoDao()
    )

    // 2. El flujo de productos se obtiene directamente del repositorio.
    val productos: StateFlow<List<Producto>> = repository.productos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // El bloque init ya no es necesario para refrescar datos desde una API.

    fun agregarAlCarrito(producto: Producto) {
        val carrito = EstadoApp.carrito
        val articuloExistente = carrito.find { it.producto.id == producto.id }

        if (articuloExistente != null) {
            val indice = carrito.indexOf(articuloExistente)
            carrito[indice] = articuloExistente.copy(cantidad = articuloExistente.cantidad + 1)
        } else {
            carrito.add(Articulo(producto, 1))
        }
    }
}
