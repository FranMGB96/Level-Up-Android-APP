package com.example.level_up.repository

import com.example.level_up.data.ProductoDao
import com.example.level_up.model.Producto
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio para gestionar los datos de los productos.
 *
 * Actúa como intermediario entre el ViewModel y la fuente de datos de los productos (el DAO),
 * proporcionando una API limpia para acceder a los datos de la aplicación.
 * En este caso, expone un Flow con la lista de todos los productos desde la base de datos Room.
 */
class ProductoRepository(private val productoDao: ProductoDao) {

    /**
     * Expone un flujo de productos directamente desde la base de datos local.
     */
    val productos: Flow<List<Producto>> = productoDao.obtenerTodos()
}
