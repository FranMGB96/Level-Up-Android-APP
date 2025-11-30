package com.example.level_up.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.level_up.model.Usuario

/**
 * DAO (Data Access Object) para la tabla de Usuarios.
 * Aquí se definen todas las operaciones de base de datos para los usuarios.
 */
@Dao
interface UsuarioDao {
    /**
     * Inserta un nuevo usuario en la base de datos.
     * Si el usuario ya existe (misma PrimaryKey), lo reemplaza.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: Usuario)

    /**
     * Actualiza un usuario existente en la base de datos.
     */
    @Update
    suspend fun actualizar(usuario: Usuario)

    /**
     * Busca y devuelve un usuario por su email.
     * La consulta SQL se define en la anotación @Query.
     */
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUsuarioPorEmail(email: String): Usuario?
}
