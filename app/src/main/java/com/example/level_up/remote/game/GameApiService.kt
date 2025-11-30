package com.example.level_up.remote.game

import com.example.level_up.model.game.GameDeal
import retrofit2.http.GET

/**
 * Interfaz que define los endpoints de la API de GamerPower utilizando Retrofit.
 *
 * Se utiliza para declarar las operaciones de red, como obtener la lista de giveaways (ofertas).
 */
interface GameApiService {
    @GET("giveaways")
    suspend fun getGiveaways(): List<GameDeal>
}
