package com.example.level_up.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Objeto para gestionar la persistencia de la sesión de usuario.
 * Utiliza SharedPreferences para guardar el email del usuario logueado.
 */
object SessionManager {

    private const val PREFS_NAME = "LevelUpPrefs"
    private const val KEY_USER_EMAIL = "user_email"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Guarda el email del usuario que ha iniciado sesión.
     */
    fun saveUserEmail(context: Context, email: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_USER_EMAIL, email)
        editor.apply()
    }

    /**
     * Obtiene el email del usuario guardado en la sesión.
     * @return El email del usuario o null si no hay ninguna sesión guardada.
     */
    fun getUserEmail(context: Context): String? {
        return getPreferences(context).getString(KEY_USER_EMAIL, null)
    }

    /**
     * Borra la sesión del usuario.
     */
    fun clearSession(context: Context) {
        val editor = getPreferences(context).edit()
        editor.remove(KEY_USER_EMAIL)
        editor.apply()
    }
}
