package com.example.mypokedex.Api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {

    /*@GET("pokemon?limit=151")
    suspend fun getPokemons():String*/

    @GET("pokemon/{number}")
    suspend fun getSpecificPokemon(@Path("number") number:String):String

    @GET("type/{typeNumber}/")
    suspend fun getTypeEffectiveness(@Path("typeNumber") typeNumber:String):String

    @GET("type/12/")
    suspend fun getTypeEffectivenessSSS():String

}

private var retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(ScalarsConverterFactory.create()).build()

var service = retrofit.create(PokeApi::class.java)!!