package com.example.level_up.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.AppDatabase
import com.example.level_up.data.EstadoApp
import com.example.level_up.data.SessionManager
import com.example.level_up.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Estado de la interfaz de usuario para el registro
data class RegistroUIState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val confirmarPassword: String = "",
    val errorNombre: String? = null,
    val errorEmail: String? = null,
    val errorPassword: String? = null,
    val errorConfirmarPassword: String? = null,
    val errorGeneral: String? = null, // Para errores que no son de un campo específico
    val registroExitoso: Boolean = false
)

class RegistrarViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()

    // StateFlow para exponer el estado de la UI de forma segura
    private val _uiState = MutableStateFlow(RegistroUIState())
    val uiState: StateFlow<RegistroUIState> = _uiState.asStateFlow()

    // --- Actualizadores de estado para cada campo ---

    fun onNombreChange(newName: String) {
        _uiState.update { it.copy(nombre = newName, errorNombre = null, errorGeneral = null) }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, errorEmail = null, errorGeneral = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, errorPassword = null, errorGeneral = null) }
    }

    fun onConfirmarPasswordChange(newConfirm: String) {
        _uiState.update { it.copy(confirmarPassword = newConfirm, errorConfirmarPassword = null, errorGeneral = null) }
    }

    fun onRegisterClick() {
        // Reiniciar los errores antes de validar
        _uiState.update { it.copy(errorNombre = null, errorEmail = null, errorPassword = null, errorConfirmarPassword = null, errorGeneral = null) }
        
        val estadoActual = _uiState.value
        var hayErrores = false

        if (estadoActual.nombre.isBlank()) {
            _uiState.update { it.copy(errorNombre = "El nombre es obligatorio") }
            hayErrores = true
        }

        if (estadoActual.email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(estadoActual.email).matches()) {
            _uiState.update { it.copy(errorEmail = "El formato del correo no es válido") }
            hayErrores = true
        }

        if (estadoActual.password.length < 6) {
            _uiState.update { it.copy(errorPassword = "La contraseña debe tener al menos 6 caracteres") }
            hayErrores = true
        }

        if (estadoActual.password != estadoActual.confirmarPassword) {
            _uiState.update { it.copy(errorConfirmarPassword = "Las contraseñas no coinciden") }
            hayErrores = true
        }

        if (hayErrores) return

        viewModelScope.launch {
            if (usuarioDao.getUsuarioPorEmail(estadoActual.email) != null) {
                _uiState.update { it.copy(errorGeneral = "El correo electrónico ya está registrado") }
                return@launch
            }

            val newUser = Usuario(estadoActual.email.trim(), estadoActual.nombre.trim(), estadoActual.password.trim())
            usuarioDao.insertar(newUser)
            EstadoApp.usuarioActual.value = newUser
            
            // Guardar la sesión del nuevo usuario
            SessionManager.saveUserEmail(getApplication(), newUser.email)
            
            _uiState.update { it.copy(registroExitoso = true) }
        }
    }
}
