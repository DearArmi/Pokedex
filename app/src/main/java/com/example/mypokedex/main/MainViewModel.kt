package com.example.mypokedex.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.Api.ApiResponseStatus
import com.example.mypokedex.DataBase.getDataBase
import com.example.mypokedex.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

private val Tag = MainViewModel::class.java.simpleName

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val dataBase = getDataBase(application)
    private val repository = MainRepository(dataBase, application)

    private var _pokemonList = MutableLiveData<MutableList<Pokemon>>()
    val pokemonList: LiveData<MutableList<Pokemon>>
        get() = _pokemonList

    /*private var _searchPokemonList = MutableLiveData<Flow<MutableList<Pokemon>>>()
    val searchPokemonList:LiveData<Flow<MutableList<Pokemon>>>
        get() = _searchPokemonList*/

    private var _status = MutableLiveData<ApiResponseStatus>()
    val status:LiveData<ApiResponseStatus>
        get() = _status

    /*init {
        loadPokemon()
    }*/

    /*private fun loadPokemon() {
        viewModelScope.launch {

            if (repository.checkDataBase() > 0) {
                _status.value = ApiResponseStatus.LOADING
                //_pokemonList.value = repository.getRegion(1,386)
                _status.value = ApiResponseStatus.DONE
            } else {
                try {

                    _status.value = ApiResponseStatus.LOADING
                    _pokemonList.value = repository.loadPokemonFromApi()
                    _status.value = ApiResponseStatus.DONE

                }catch (e:UnknownHostException){
                    _status.value = ApiResponseStatus.ERROR
                    Log.d(Tag,"No internet", e)
                }

            }

        }
    }*/
    fun delete(){

        viewModelScope.launch(Dispatchers.IO) {
            repository.delete()
        }

    }

    fun searchPokemon(characters:String):LiveData<MutableList<Pokemon>>{
        return repository.searchPokemon2(characters)
    }
/*
    fun searchPokemon(characters:String){
        viewModelScope.launch(Dispatchers.Main) {

            _searchPokemonList.value = repository.searchPokemon2(characters)
        }
    }*/

    fun getByRegion(valueOne:Int, valueTwo:Int, totalPokemon:Int){
        viewModelScope.launch(Dispatchers.Main) {

            try {
                _status.value = ApiResponseStatus.LOADING
                _pokemonList.value = repository.getRegion(valueOne, valueTwo, totalPokemon)
                _status.value = ApiResponseStatus.DONE
            }catch (e:UnknownHostException){
                _status.value = ApiResponseStatus.ERROR
                //_pokemonList.value?.removeAll(mutableListOf<Pokemon>())
                Log.d(Tag,"No internet or download incomplete", e)
            }
        }}

}

