package com.example.mypokedex

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.DataBase.getDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val dataBase = getDataBase(application)
    private val repository = MainRepository(dataBase)

    private var _pokemonList = MutableLiveData<MutableList<Pokemon>>()
    val pokemonList: LiveData<MutableList<Pokemon>>
        get() = _pokemonList

    init {
        loadPokemon()
    }

    private fun loadPokemon() {
        viewModelScope.launch {

            if (repository.checkDataBase() > 0) {
                _pokemonList.value = repository.getRegion(1,386)
            } else {
                _pokemonList.value = repository.loadPokemon()
            }

        }
    }

    fun getByRegion(valueOne:Int, valueTwo:Int){
        viewModelScope.launch(Dispatchers.Main) {

            _pokemonList.value = repository.getRegion(valueOne, valueTwo)
        }}

}

