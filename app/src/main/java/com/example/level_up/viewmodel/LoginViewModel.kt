package com.example.level_up.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.AppDatabase
import com.example.level_up.data.EstadoApp
import com.example.level_up.data.SessionManager
import com.example.level_up.data.UsuarioDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao: UsuarioDao

    init {
        usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
    }

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var error by mutableStateOf<String?>(null)
        private set

    private val _loginSuccess = Channel<Unit>()
    val loginSuccess = _loginSuccess.receiveAsFlow()

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onLoginClick() {
        viewModelScope.launch {
            val cleanEmail = email.trim()
            val cleanPassword = password.trim()

            if (cleanEmail.isBlank() || cleanPassword.isBlank()) {
                error = "El correo y la contraseña no pueden estar vacíos"
                return@launch
            }

            val usuarioEncontrado = usuarioDao.getUsuarioPorEmail(cleanEmail)

            if (usuarioEncontrado != null && usuarioEncontrado.password == cleanPassword) {
                EstadoApp.usuarioActual.value = usuarioEncontrado
                // Guardar la sesión del usuario
                SessionManager.saveUserEmail(getApplication(), usuarioEncontrado.email)
                _loginSuccess.send(Unit)
                error = null
            } else {
                error = "Credenciales inválidas"
            }
        }
    }
}
