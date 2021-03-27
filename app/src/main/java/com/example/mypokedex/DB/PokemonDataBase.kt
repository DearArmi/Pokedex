package com.example.mypokedex.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mypokedex.Pokemon

@Database(entities = [Pokemon::class], version = 1)
abstract class PokemonDataBase:RoomDatabase(){

    abstract val pokemonDao:PokemonDao

}
    private lateinit var INSTANCE:PokemonDataBase

    fun getDataBase(context: Context):PokemonDataBase{

        synchronized(PokemonDataBase::class.java){

            if (!::INSTANCE.isInitialized){

                INSTANCE = Room.databaseBuilder(context.applicationContext, PokemonDataBase::class.java, "PokemonDB").build()

            }

        }
        return INSTANCE
    }