package com.example.mypokedex.DataBase

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mypokedex.Pokemon


@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemon:Pokemon)

    //Testing
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonList(list: MutableList<Pokemon>)

    @Query("SELECT * FROM Pokemon")
    fun getAllPokemon():MutableList<Pokemon>

    //flow
    /*@Query("SELECT * FROM Pokemon WHERE name LIKE :pokemonName")
    fun searchPokemon(pokemonName:String): LiveData<Flow<MutableList<Pokemon>>>*/

    ////TODO--implement this with Dagger
    @Query("SELECT * FROM Pokemon WHERE name LIKE :pokemonName")
    fun getPokemonLikeSearch(pokemonName:String):MutableList<Pokemon>

    @Query("SELECT * FROM Pokemon WHERE name LIKE :pokemonName")
    fun searchPokemon2(pokemonName:String):LiveData<MutableList<Pokemon>>

    @Query("SELECT * FROM Pokemon WHERE number BETWEEN :valueOne AND :valueTwo")
    fun getPokemonByRegion(valueOne:Int, valueTwo:Int):MutableList<Pokemon>

    @Query("SELECT COUNT(number) FROM Pokemon WHERE number BETWEEN :valueOne AND :valueTwo")
    fun checkRegionInDataBase(valueOne:Int, valueTwo:Int):Int

    @Query("DELETE FROM Pokemon WHERE number BETWEEN 1 AND 151")
    fun delete()

    //Use this when you add another column into pokemon table
    @Query("UPDATE Pokemon SET pokemonPreview = :preview WHERE pokemonPreview = 0")
    fun editPokemon(preview: Bitmap)

}