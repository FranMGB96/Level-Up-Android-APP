package com.example.level_up.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.AppDatabase
import com.example.level_up.data.EstadoApp
import com.example.level_up.data.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Enum para representar el estado de la sesión
enum class SessionState {
    LOADING,      // Comprobando si hay una sesión
    LOGGED_IN,    // Sesión activa encontrada
    LOGGED_OUT    // No hay sesión
}

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()

    private val _sessionState = MutableStateFlow(SessionState.LOADING)
    val sessionState = _sessionState.asStateFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            val userEmail = SessionManager.getUserEmail(getApplication())
            if (userEmail == null) {
                // No hay email guardado, no hay sesión
                _sessionState.value = SessionState.LOGGED_OUT
                return@launch
            }

            // Hay un email, intentamos cargar el usuario
            val usuario = usuarioDao.getUsuarioPorEmail(userEmail)
            if (usuario != null) {
                // Usuario encontrado, restauramos la sesión
                EstadoApp.usuarioActual.value = usuario
                _sessionState.value = SessionState.LOGGED_IN
            } else {
                // El email estaba guardado pero el usuario no está en la BD (raro, pero posible)
                SessionManager.clearSession(getApplication())
                _sessionState.value = SessionState.LOGGED_OUT
            }
        }
    }
}
