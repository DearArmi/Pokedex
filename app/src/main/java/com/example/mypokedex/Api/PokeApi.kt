package com.example.mypokedex.Api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface PokeApi {

    @GET("pokemon?limit=151")
    suspend fun getPokemons():String

    @GET("pokemon/")
    suspend fun getSpecificPokemon(number: String):String

}

private var retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(ScalarsConverterFactory.create()).build()

var service = retrofit.create(PokeApi::class.java)