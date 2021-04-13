package com.example.mypokedex

import com.example.mypokedex.Api.service
import com.example.mypokedex.DataBase.PokemonDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainRepository(private val dataBase: PokemonDataBase) {

    private suspend fun loadPokemonFromApi(valueOne: Int, valueTwo: Int):MutableList<Pokemon>{
        return withContext(Dispatchers.IO){

            var pokemonList = mutableListOf<Pokemon>()

            for (i in valueOne until valueTwo+1){
                val pokemonString = service.getSpecificPokemon("$i")
                val pokemon = parsePokemon(pokemonString)
                dataBase.pokemonDao.insertPokemon(pokemon)
            }

            pokemonList = getPokemonListFromDB(valueOne, valueTwo)
            pokemonList

        }
    }
    private suspend fun checkDataBase(valueOne: Int, valueTwo: Int):Int{
        return withContext(Dispatchers.IO){

            dataBase.pokemonDao.checkRegionInDataBase(valueOne, valueTwo)
        }
    }

    private suspend  fun getPokemonListFromDB(valueOne: Int, valueTwo: Int):MutableList<Pokemon> {

        return withContext(Dispatchers.IO){

            dataBase.pokemonDao.getPokemonByRegion(valueOne, valueTwo)
        }

    }

    suspend fun getRegion(valueOne:Int, valueTwo:Int):MutableList<Pokemon>{
        return withContext(Dispatchers.IO){

            var pokemonList = mutableListOf<Pokemon>()
            //TODO----SET cCONDITION BY REGION
            pokemonList = if(checkDataBase(valueOne, valueTwo)>0){

                dataBase.pokemonDao.getPokemonByRegion(valueOne, valueTwo)

            }else{
                loadPokemonFromApi(valueOne, valueTwo)
            }
            pokemonList
        }

    }

    fun delete(){
        dataBase.pokemonDao.delete()
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


}