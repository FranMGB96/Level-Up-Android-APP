package com.example.level_up.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UbicacionViewModel : ViewModel() {
    var latitud by mutableStateOf<Double?>(null)
        private set
    var longitud by mutableStateOf<Double?>(null)
        private set

    fun actualizarUbicacion(lat: Double, lon: Double) {
        latitud = lat
        longitud = lon
    }
}
