package com.example.mypokedex

import com.example.mypokedex.Api.service
import com.example.mypokedex.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainRepository {

    suspend fun loadPokemon():MutableList<Pokemon>{
        return withContext(Dispatchers.IO){

            var x = 4
            val pokemonString = service.getPokemons()
            val pokemonList = parsePokemon(pokemonString)

            pokemonList

        }
    }

    private fun parsePokemon(pokemonString: String): MutableList<Pokemon> {

        val pokemonList = mutableListOf<Pokemon>()
        val types = listOf<String>("water", "water")

        val pokemonJsonObject = JSONObject(pokemonString)
        val pokemonArray = pokemonJsonObject.getJSONArray("results")

        for (i in 0 until 151){

            val pokemonObject = pokemonArray[i] as JSONObject
            val name = pokemonObject.getString("name")

            pokemonList.add(Pokemon(i+1, name, types))

        }

        return pokemonList
    }

    private fun parsePokemon2(pokemonString: String): MutableList<Pokemon> {

        val pokemonList = mutableListOf<Pokemon>()
        val types = listOf<String>("water", "water")

        val pokemonJsonObject = JSONObject(pokemonString)
        val pokemonArray = pokemonJsonObject.getJSONArray("results")

        for (i in 0 until 151){

            val pokemonObject = pokemonArray[i] as JSONObject
            val name = pokemonObject.getString("name")

            pokemonList.add(Pokemon(i+1, name, types))

        }

        return pokemonList
    }

    /*fun loadPokemon2():MutableList<Pokemon>{

        val listType = listOf<String>("water", "electric")
        val listType2 = listOf<String>("electric")
        val listType3 = listOf<String>("electric","electric")
        val pokemonList = mutableListOf<Pokemon>()

        pokemonList.add(Pokemon(1, "Bulbasaur", listType, 1,1,1,1,1,1,""))
        pokemonList.add(Pokemon(1, "yvysur", listType, 1,1,1,1,1,1,""))
        pokemonList.add(Pokemon(1, "Buaur", listType, 1,1,1,1,1,1,""))
        pokemonList.add(Pokemon(1, "charmander", listType, 1,1,1,1,1,1,""))
        pokemonList.add(Pokemon(1, "Bul", listType, 1,1,1,1,1,1,""))

        return pokemonList
    }*/
}