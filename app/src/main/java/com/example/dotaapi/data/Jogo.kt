package com.example.dotaapi.data

import com.squareup.moshi.Json

data class Jogo(
    val id: Int,
    @Json(name = "title") val name: String,
    @Json(name = "thumbnail") val img: String,
    @Json(name = "short_description") val description: String,
    val platform: String,
    val developer: String,
)

