package com.example.mypokedex.DB

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

}