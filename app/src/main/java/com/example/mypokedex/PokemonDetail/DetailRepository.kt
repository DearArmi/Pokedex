package com.example.mypokedex.PokemonDetail

import com.example.mypokedex.Api.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class DetailRepository {

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

    //TODO-- include JsonArray in loop method
    private fun parseTypeEffectiveness(pokemonEffectiveness:String):MutableList<Array<String>> {

        val doubleDamageFromAndToByTypes = mutableListOf<Array<String>>()
        var position = 0

        val typeEffectiveness = JSONObject(pokemonEffectiveness)
        val damageRelation = typeEffectiveness.getJSONObject("damage_relations")

        //Pokemon Type double damage from
        val doubleDamageFromApi = damageRelation.getJSONArray("double_damage_from")
        val doubleDamageFrom = getArray(doubleDamageFromApi)
        doubleDamageFromAndToByTypes.add(position,doubleDamageFrom)
        position++
        //Pokemon Type double damage to
        val doubleDamageToApi = damageRelation.getJSONArray("double_damage_to")
        val doubleDamageTo = getArray(doubleDamageToApi)
        doubleDamageFromAndToByTypes.add(position,doubleDamageTo)
        position++
        //Pokemon Type half damage from
        val halfDamageFromApi = damageRelation.getJSONArray("half_damage_from")
        val halfDamageFrom = getArray(halfDamageFromApi)
        doubleDamageFromAndToByTypes.add(position,halfDamageFrom)
        position++
        //Pokemon Type half damage to
        val halfDamageToApi = damageRelation.getJSONArray("half_damage_to")
        val halfDamageTo = getArray(halfDamageToApi)
        doubleDamageFromAndToByTypes.add(position, halfDamageTo)
        position++
        //Pokemon Type no damage from
        val noDamageFromApi = damageRelation.getJSONArray("no_damage_from")
        val noDamageFrom = getArray(noDamageFromApi)
        doubleDamageFromAndToByTypes.add(position, noDamageFrom)
        position++
        //Pokemon Type no damage to
        val noDamageToApi = damageRelation.getJSONArray("no_damage_to")
        val noDamageTo = getArray(noDamageToApi)
        doubleDamageFromAndToByTypes.add(position, noDamageTo)

        return doubleDamageFromAndToByTypes
    }

    private fun getArray(jsonArray: JSONArray): Array<String>{

        val effectiveness = arrayOf("","","","","")

        if(jsonArray.length() > 0) {
            for (i in 0 until jsonArray.length()) {
                val pokemonType = jsonArray[i] as JSONObject
                val type = pokemonType.getString("name")

                effectiveness[i] = type
            }
        }
        return effectiveness
    }


}