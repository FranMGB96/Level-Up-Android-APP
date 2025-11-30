package com.example.level_up.model.game

import com.google.gson.annotations.SerializedName

/**
 * Representa los datos de una oferta o giveaway de un juego.
 *
 * Esta data class se usa para mapear la respuesta de la API a un objeto Kotlin,
 * conteniendo todos los detalles relevantes de la oferta, como el título, la descripción,
 * las plataformas, la fecha de finalización y las URL para acceder a ella.
 */
data class GameDeal(
    val id: Int,
    val title: String,
    val worth: String,
    val thumbnail: String,
    val image: String,
    val description: String,
    val instructions: String,
    @SerializedName("open_giveaway_url") val openGiveawayUrl: String,
    @SerializedName("published_date") val publishedDate: String,
    val type: String,
    val platforms: String,
    @SerializedName("end_date") val endDate: String,
    val users: Int,
    val status: String,
    @SerializedName("gamerpower_url") val gamerpowerUrl: String,
    @SerializedName("open_giveaway") val openGiveaway: String
)
