package com.example.mypokedex.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mypokedex.DataBase.PokemonDao
import com.example.mypokedex.Pokemon

@Database(entities = [Pokemon::class], version = 1)
@TypeConverters(Converters::class)
abstract class PokemonDataBase:RoomDatabase(){

    abstract val pokemonDao: PokemonDao

}
    private lateinit var INSTANCE: PokemonDataBase

    fun getDataBase(context: Context):PokemonDataBase{

        synchronized(PokemonDataBase::class.java){

            if (!::INSTANCE.isInitialized){

                INSTANCE = Room.databaseBuilder(context.applicationContext, PokemonDataBase::class.java, "pokemon_db").build()

            }
            return INSTANCE
        }

    }