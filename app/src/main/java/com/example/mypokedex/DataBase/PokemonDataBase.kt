package com.example.mypokedex.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mypokedex.Pokemon

@Database(entities = [Pokemon::class], version = 2)
@TypeConverters(Converters::class)
abstract class PokemonDataBase:RoomDatabase(){

    abstract val pokemonDao: PokemonDao

}
    private lateinit var INSTANCE: PokemonDataBase

    //TODO---FIX MIGRATION, is working but not replacing all existing data an Update could be the solution
    val migration1_2:Migration = object :Migration(1,2){

        //Blob is type used to store images, sounds, videos...
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE 'Pokemon' ADD COLUMN 'pokemonPreview' BLOB DEFAULT '0'")
        }

    }

    fun getDataBase(context: Context):PokemonDataBase{

        synchronized(PokemonDataBase::class.java){

            if (!::INSTANCE.isInitialized){

                INSTANCE = Room.databaseBuilder(context.applicationContext, PokemonDataBase::class.java, "pokemon_db")
                        .addMigrations(migration1_2)
                        .build()

            }
            return INSTANCE
        }

    }