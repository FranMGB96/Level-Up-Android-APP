package com.example.level_up.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.level_up.model.Producto
import kotlinx.coroutines.flow.Flow

/**
 * DAO para la tabla de Productos.
 */
@Dao
interface ProductoDao {
    /**
     * Inserta una lista de productos. Si ya existen, los ignora.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarTodos(productos: List<Producto>)

    /**
     * Actualiza un producto existente en la base de datos.
     */
    @Update
    suspend fun actualizar(producto: Producto)

    /**
     * Obtiene todos los productos de la tabla, ordenados por nombre.
     * Devuelve un Flow, lo que significa que la UI que observe este dato
     * se actualizará automáticamente si los datos cambian.
     */
    @Query("SELECT * FROM productos ORDER BY nombre ASC")
    fun obtenerTodos(): Flow<List<Producto>>

    /**
     * Cuenta el número total de productos en la tabla.
     */
    @Query("SELECT COUNT(*) FROM productos")
    suspend fun count(): Int
}
