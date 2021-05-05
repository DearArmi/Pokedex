package com.example.mypokedex.PokemonDetail

import com.example.mypokedex.Api.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class DetailRepository {

    //TODO CHANGE THIS TO RETURN A LIST OF ARRAYS
    suspend fun loadPokemonTypeEffectivenessFromAPI(pokemonTypes: IntArray):MutableList<MutableList<Array<String>>>{

        return withContext(Dispatchers.IO){
            //List of weaknesses and strengths by pokemon
            val damageFromAndToByTypes = mutableListOf<MutableList<Array<String>>>()

            //getting weaknesses and strengths by type
            for (i in pokemonTypes.indices){
                val typeIndex = pokemonTypes[i]

                //There's no Type 0 in Api
                    if(typeIndex > 0){
                        val effectivenessString = service.getTypeEffectiveness("$typeIndex")
                        val typeEffectiveness = parseTypeEffectiveness(effectivenessString)
                        damageFromAndToByTypes.add(i, typeEffectiveness)
                    }


            }
           damageFromAndToByTypes
        }
    }



    //TODO create a method for loops
    private fun parseTypeEffectiveness(pokemonEffectiveness:String):MutableList<Array<String>> {

        //val doubleDamageFrom = arrayOf("","","","","")
        //val doubleDamageTo = arrayOf("","","","","")
        val doubleDamageFromAndToByTypes = mutableListOf<Array<String>>()
        var position = 0

        val typeEffectiveness = JSONObject(pokemonEffectiveness)
        val damageRelation = typeEffectiveness.getJSONObject("damage_relations")
        val doubleDamageFromApi = damageRelation.getJSONArray("double_damage_from")

        val doubleDamageFrom = getArray(doubleDamageFromApi)
        /*for (i in 0 until doubleDamageFromApi.length()){
            val pokemonType = doubleDamageFromApi[i] as JSONObject
            val type = pokemonType.getString("name")

            doubleDamageFrom[i] = type
        }*/
        doubleDamageFromAndToByTypes.add(position,doubleDamageFrom)
        position++

        val doubleDamageToApi = damageRelation.getJSONArray("double_damage_to")
        val doubleDamageTo = getArray(doubleDamageToApi)

        /*for (i in 0 until doubleDamageToApi.length()){
            val pokemonType = doubleDamageToApi[i] as JSONObject
            val type = pokemonType.getString("name")

            doubleDamageTo[i] = type
        }*/
        doubleDamageFromAndToByTypes.add(position,doubleDamageTo)
        position++

        return doubleDamageFromAndToByTypes
    }

    private fun getArray(doubleDamageFromApi: JSONArray): Array<String>{

        val effectiveness = arrayOf("","","","","")

        for (i in 0 until doubleDamageFromApi.length()){
            val pokemonType = doubleDamageFromApi[i] as JSONObject
            val type = pokemonType.getString("name")

            effectiveness[i] = type
        }
        return effectiveness
    }


}