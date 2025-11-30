package com.example.level_up.remote.game

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente de Retrofit para consumir la API de GamerPower.
 *
 * Este objeto singleton se encarga de crear y configurar una instancia de Retrofit,
 * que a su vez genera una implementación de la interfaz `GameApiService`.
 */
object GameApiClient {
    private const val BASE_URL = "https://www.gamerpower.com/api/"

    val service: GameApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(GameApiService::class.java)
    }
}
