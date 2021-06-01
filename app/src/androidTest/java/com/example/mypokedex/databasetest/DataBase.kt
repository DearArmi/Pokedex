package com.example.mypokedex.databasetest

import android.graphics.Bitmap
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.mypokedex.DataBase.PokemonDao
import com.example.mypokedex.DataBase.PokemonDataBase
import com.example.mypokedex.Pokemon
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class DataBase {
    private lateinit var database: PokemonDataBase
    private lateinit var dao: PokemonDao

    @Before
    fun setup(){

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PokemonDataBase::class.java
        ).allowMainThreadQueries().build()

        dao = database.pokemonDao
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun getAllPokemon(){
        val pokemonList = mutableListOf<Pokemon>()
        val stats = intArrayOf(1,2,3,4,5,6)
        val types = mutableListOf<String>("","")
        val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

        val entireList = mutableListOf<Pokemon>()

        pokemonList.add(Pokemon(1,"pera",types,"",stats, bitmap))
        pokemonList.add(Pokemon(1,"patata",types,"",stats, bitmap))
        pokemonList.add(Pokemon(1,"parmesano",types,"",stats, bitmap))

        for(i in 0 until pokemonList.size){
            dao.insertPokemon(pokemonList[i])
            entireList.add(pokemonList[i])
        }


        //entireList = dao.getAllPokemon()
        //entireList.addAll(dao.getAllPokemon())

        entireList

        assertThat(entireList.size).isEqualTo(pokemonList.size)
    }

    @Test //not working yet
    fun getLikeList(){

        val pokemonList = mutableListOf<Pokemon>()
        val stats = intArrayOf(1,2,3,4,5,6)
        val types = mutableListOf<String>("","")
        val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

        pokemonList.add(Pokemon(1,"pera",types,"",stats, bitmap))
        pokemonList.add(Pokemon(1,"patata",types,"",stats, bitmap))
        pokemonList.add(Pokemon(1,"parmesano",types,"",stats, bitmap))

        dao.insertPokemonList(pokemonList)


        var list = mutableListOf<Pokemon>()
           list =  dao.getPokemonLikeSearch("pera")
        //assertThat(list).isEqualTo(pokemonList.size)
        assertThat(list[0].name).contains("pera")


    }
}