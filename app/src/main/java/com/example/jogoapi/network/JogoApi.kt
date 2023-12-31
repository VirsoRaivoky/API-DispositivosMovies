package com.example.jogoapi.network

import com.example.jogoapi.data.Jogo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://mmobomb.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface JogoApiService {
    @GET("/api1/games?category=mmo")
    suspend fun getJogos(): List<Jogo>
}

object JogoApi {
    val retrofitService: JogoApiService by lazy {
        retrofit.create(JogoApiService::class.java)
    }
}