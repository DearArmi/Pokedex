package com.example.mypokedex.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mypokedex.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemon:Pokemon)

    @Query("SELECT * FROM Pokemon")
    fun getAllPokemon():MutableList<Pokemon>

    @Query("SELECT * FROM Pokemon WHERE number BETWEEN :valueOne AND :valueTwo")
    fun getPokemonByRegion(valueOne:Int, valueTwo:Int):MutableList<Pokemon>

    @Query("SELECT COUNT(number) FROM Pokemon")
    fun checkDataBase():Int

}