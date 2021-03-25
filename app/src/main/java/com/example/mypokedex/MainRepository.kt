package com.example.mypokedex

import com.example.mypokedex.Pokemon

class MainRepository {

    fun loadPokemon():MutableList<Pokemon>{

        val listType = listOf<String>("water", "electric")
        val listType2 = listOf<String>("electric")
        val listType3 = listOf<String>("electric","electric")
        val pokemonList = mutableListOf<Pokemon>()

        pokemonList.add(Pokemon(1, "Bulbasaur", listType3, 1,1,1,1,1,1,""))
        pokemonList.add(Pokemon(1, "yvysur", listType2, 1,1,1,1,1,1,""))
        pokemonList.add(Pokemon(1, "Buaur", listType, 1,1,1,1,1,1,""))
        pokemonList.add(Pokemon(1, "charmander", listType3, 1,1,1,1,1,1,""))
        pokemonList.add(Pokemon(1, "Bul", listType2, 1,1,1,1,1,1,""))

        return pokemonList
    }

    fun loadPokemon2():MutableList<Pokemon>{

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
    }
}