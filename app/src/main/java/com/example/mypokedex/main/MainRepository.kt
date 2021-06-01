package com.example.mypokedex.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.LiveData
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.mypokedex.Api.service
import com.example.mypokedex.DataBase.PokemonDataBase
import com.example.mypokedex.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainRepository(private val dataBase: PokemonDataBase, private val application:Context) {

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

   /* suspend fun searchPokemon(pokemonName:String): Flow<MutableList<Pokemon>> {
        return withContext(Dispatchers.IO){

            dataBase.pokemonDao.searchPokemon(pokemonName)
        }
    }*/

    //flow
    /*fun searchPokemon(pokemonName:String):LiveData<Flow<MutableList<Pokemon>>>{
            return  dataBase.pokemonDao.searchPokemon(pokemonName)

    }*/

    suspend fun getAllPokemon():MutableList<Pokemon>{
        return withContext(Dispatchers.IO){
            dataBase.pokemonDao.getAllPokemon()
        }
    }

    suspend fun getPokemonLikeSearch(characters:String):MutableList<Pokemon>{
        var pokemonLikeList = mutableListOf<Pokemon>()
        return  withContext(Dispatchers.IO){

            pokemonLikeList = dataBase.pokemonDao.getPokemonLikeSearch(characters)
            pokemonLikeList
        }

    }

    fun searchPokemon2(pokemonName:String):LiveData<MutableList<Pokemon>>{


            return dataBase.pokemonDao.searchPokemon2(pokemonName)

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

    suspend fun getRegion(valueOne:Int, valueTwo:Int, totalPokemon:Int):MutableList<Pokemon>{
        return withContext(Dispatchers.IO){

            var pokemonList = mutableListOf<Pokemon>()
            //TODO----SET CONDITION BY REGION
            pokemonList = if(checkDataBase(valueOne, valueTwo) == totalPokemon){

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

    private suspend fun parsePokemon(pokemonString: String): Pokemon {

        val types = mutableListOf<String>("","")
        val allStats = IntArray(6)
        val pokemonPreviewImage:Bitmap

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

        //Getting PreviewImage
        val version = pokemonSprites.getJSONObject("versions")
        val generation = version.getJSONObject("generation-v")
        val blackAndWhite = generation.getJSONObject("black-white")
        val pokemonPreview = blackAndWhite.getString("front_default")//.toByteArray()
        pokemonPreviewImage = getBitmap(pokemonPreview)

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


        return Pokemon(pokemonNumber, pokemonName, types, pokemonImage, allStats, pokemonPreviewImage)
    }

    //TODO----FIND A WAY TO TAKE THIS OUT OF HERE

    private suspend fun getBitmap(url:String):Bitmap{
        val loading = ImageLoader(application)
        val request = ImageRequest.Builder(application).data(url).build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap

    }


}