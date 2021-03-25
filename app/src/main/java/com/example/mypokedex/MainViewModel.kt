package com.example.mypokedex

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mypokedex.MainRepository
import com.example.mypokedex.Pokemon


class MainViewModel(application: Application): AndroidViewModel(application) {

    //TODO--database
    val repository = MainRepository()

    private var _pokemonList = MutableLiveData<MutableList<Pokemon>>()
    val pokemonList: LiveData<MutableList<Pokemon>>
        get() = _pokemonList

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        _pokemonList.value = repository.loadPokemon()
    }
    fun load2(){
        _pokemonList.value = repository.loadPokemon2()
    }

}