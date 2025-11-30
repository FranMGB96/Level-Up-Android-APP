package com.example.level_up.repository

import com.example.level_up.model.game.GameDeal
import com.example.level_up.remote.game.GameApiClient

/**
 * Repositorio para gestionar los datos de los juegos.
 *
 * Se encarga de obtener los datos de las ofertas de juegos (giveaways)
 * desde el cliente de la API (`GameApiClient`).
 */
class GameRepository {
    suspend fun getGiveaways(): List<GameDeal> {
        return try {
            GameApiClient.service.getGiveaways()
        } catch (e: Exception) {
            // En un caso real, aquí manejarías el error de forma más elegante
            // (logs, retornar un estado de error, etc.)
            emptyList()
        }
    }
}
