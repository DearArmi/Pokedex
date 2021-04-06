package com.example.mypokedex

import com.example.mypokedex.Api.service
import com.example.mypokedex.DataBase.PokemonDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainRepository(private val dataBase: PokemonDataBase) {

    suspend fun loadPokemon():MutableList<Pokemon>{
        return withContext(Dispatchers.IO){

            //var pokemonList = mutableListOf<Pokemon>()
            for (i in 1 until 386){
            val pokemonString = service.getSpecificPokemon("$i")
            val pokemon = parsePokemon(pokemonString)
            dataBase.pokemonDao.insertPokemon(pokemon)
            }

            getPokemonListFromDB()

        }
    }
    suspend fun checkDataBase():Int{
        return withContext(Dispatchers.IO){

            dataBase.pokemonDao.checkDataBase()
        }
    }

    suspend  fun getPokemonListFromDB():MutableList<Pokemon> {

        return withContext(Dispatchers.IO){

            dataBase.pokemonDao.getPokemonByRegion(1,151)
        }

    }

    suspend fun getRegion(valueOne:Int, valueTwo:Int):MutableList<Pokemon>{
        return withContext(Dispatchers.IO){
            dataBase.pokemonDao.getPokemonByRegion(valueOne, valueTwo)
        }

    }

    private fun parsePokemon(pokemonString: String): Pokemon {

        val types = mutableListOf<String>("","")
        val allStats = IntArray(6)

        val pokemonJsonObject = JSONObject(pokemonString)
        /*val pokemonArray = pokemonJsonObject.getJSONArray("forms")
        val pokemon = pokemonArray[0] as JSONObject
        val pokemonName = pokemon.getString("name")*/
        val pokemonNumber = pokemonJsonObject.getInt("id")
        val pokemonName = pokemonJsonObject.getString("name")

        //Getting image URL
        val pokemonSprites = pokemonJsonObject.getJSONObject("sprites")
        val pokemonOther = pokemonSprites.getJSONObject("other")
        val pokemonOfficial = pokemonOther.getJSONObject("official-artwork")
        val pokemonImage = pokemonOfficial.getString("front_default")

        //Getting stats
        val pokemonStatsArray = pokemonJsonObject.getJSONArray("stats")
        for (i in 0 until pokemonStatsArray.length()){
            val pokemonStat = pokemonStatsArray[i] as JSONObject
            val stat = pokemonStat.getInt("base_stat")

            allStats[i] = stat
        }

        //Getting types
        val pokemonTypesArray = pokemonJsonObject.getJSONArray("types")
        val pokemonTypeObject1 = pokemonTypesArray[0] as JSONObject
        val pokemonTypeObject11 = pokemonTypeObject1.getJSONObject("type")
        val type1 = pokemonTypeObject11.getString("name")

        types[0] = type1
        //types.add(type1)

        if (pokemonTypesArray.length() > 1){

            val pokemonTypeObject2 = pokemonTypesArray[1] as JSONObject
            val pokemonTypeObject22 = pokemonTypeObject2.getJSONObject("type")
            val type2 = pokemonTypeObject22.getString("name")
            types[1] = type2
            //types.add(type2)
        }


        return Pokemon(pokemonNumber, pokemonName, types, pokemonImage, allStats)
    }

    private fun parsePokemon2(pokemonString: String): MutableList<Pokemon> {

        val pokemonList = mutableListOf<Pokemon>()
        val types = listOf<String>("water", "water")

        val pokemonJsonObject = JSONObject(pokemonString)
        val pokemonArray = pokemonJsonObject.getJSONArray("results")

        for (i in 0 until 151){

            val pokemonObject = pokemonArray[i] as JSONObject
            val name = pokemonObject.getString("name")

            //pokemonList.add(Pokemon(i+1, name, types))

        }

        return pokemonList
    }

}