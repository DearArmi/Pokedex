package com.example.mypokedex.DataBase

import androidx.room.*
import com.example.mypokedex.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemon:Pokemon)

    @Query("SELECT * FROM Pokemon")
    fun getAllPokemon():MutableList<Pokemon>

    @Query("SELECT * FROM Pokemon WHERE number BETWEEN :valueOne AND :valueTwo")
    fun getPokemonByRegion(valueOne:Int, valueTwo:Int):MutableList<Pokemon>

    @Query("SELECT COUNT(number) FROM Pokemon WHERE number BETWEEN :valueOne AND :valueTwo")
    fun checkRegionInDataBase(valueOne:Int, valueTwo:Int):Int

    @Query("DELETE FROM Pokemon WHERE number BETWEEN 1 AND 151")
    fun delete()

}