package com.example.level_up.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey
    val email: String,
    var nombre: String, // Permite que el nombre se pueda modificar
    val password: String,
    val isAdmin: Boolean = false // Nuevo campo para identificar administradores
)
