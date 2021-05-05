package com.example.mypokedex.PokemonDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.mypokedex.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class DetailViewModel(): ViewModel() {

    private val tag = DetailViewModel::class.java.simpleName
    private val repository = DetailRepository()

    /*private var _pokemonEffectivenessList = MutableLiveData<Array<String>>()
    val pokemonEffectivenessList:LiveData<Array<String>>
        get() = _pokemonEffectivenessList*/

    private var _pokemonEffectivenessList = MutableLiveData<MutableList<MutableList<Array<String>>>>()
    val pokemonEffectivenessList:LiveData<MutableList<MutableList<Array<String>>>>
        get() = _pokemonEffectivenessList


    fun loadTypeEffectiveness(pokemonTypes:MutableList<String>){

        viewModelScope.launch(Dispatchers.Main) {

            try {
                _pokemonEffectivenessList.value = repository.loadPokemonTypeEffectivenessFromAPI(convertPokemonTypeToNumbers(pokemonTypes))
            }catch (e:UnknownHostException){
                Log.d(tag,"No internet or download incomplete", e)
            }
        }
    }


    private fun convertPokemonTypeToNumbers(pokemonTypes:MutableList<String>):IntArray{

        //TODO---there's a better way to do this
        val typeArray = IntArray(2)

        for (i in 0 until pokemonTypes.size) {

            when(pokemonTypes[i]) {
               "normal" -> typeArray[i] = 1
                "fighting" -> typeArray[i] = 2
               "flying" -> typeArray[i] = 3
                "poison" -> typeArray[i] = 4
               "ground" -> typeArray[i] = 5
                "rock" -> typeArray[i] = 6
                "bug" -> typeArray[i] = 7
               "ghost" -> typeArray[i] = 8
               "steel" -> typeArray[i] = 9
                "fire" -> typeArray[i] = 10
                "water" -> typeArray[i] = 11
                "grass" -> typeArray[i] = 12
                "electric" -> typeArray[i] = 13
                "psychic" -> typeArray[i] = 14
                "ice" -> typeArray[i] = 15
                "dragon" -> typeArray[i] = 16
                "dark" -> typeArray[i] = 17
                "fairy" -> typeArray[i] = 18
                else -> typeArray[i] = 0
            }
        }

        return typeArray
    }
}