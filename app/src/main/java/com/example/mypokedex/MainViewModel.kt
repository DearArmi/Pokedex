package com.example.mypokedex

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.DB.PokemonDataBase
import com.example.mypokedex.DB.getDataBase
import com.example.mypokedex.MainRepository
import com.example.mypokedex.Pokemon
import kotlinx.coroutines.launch


class MainViewModel(application: Application): AndroidViewModel(application) {

    val dataBase = getDataBase(application)
    val repository = MainRepository(dataBase)

    private var _pokemonList = MutableLiveData<MutableList<Pokemon>>()
    val pokemonList: LiveData<MutableList<Pokemon>>
        get() = _pokemonList

    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        viewModelScope.launch {

            _pokemonList.value = repository.loadPokemon()

        }
    }
    /*fun load2(){
        _pokemonList.value = repository.loadPokemon2()
    }*/

}