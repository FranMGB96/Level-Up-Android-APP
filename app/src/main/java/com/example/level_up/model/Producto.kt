package com.example.level_up.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos") // Etiqueta para definir la clase como una tabla.
data class Producto(
    @PrimaryKey(autoGenerate = true) // El ID será generado automáticamente por Room.
    val id: Int = 0,
    var nombre: String,
    var precio: Double,
    @DrawableRes val imagen: Int
)
