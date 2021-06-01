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
    //Searching lists
    lateinit var allPokemonList:MutableList<Pokemon>
    lateinit var tempPokemonList:MutableList<Pokemon>

    //Observers
    private var _pokemonList = MutableLiveData<MutableList<Pokemon>>()
    val pokemonList: LiveData<MutableList<Pokemon>>
        get() = _pokemonList

    private var _status = MutableLiveData<ApiResponseStatus>()
    val status:LiveData<ApiResponseStatus>
        get() = _status

    init {
        loadPokemon()
        tempPokemonList = mutableListOf<Pokemon>()
    }

    private fun loadPokemon() {
        viewModelScope.launch {
            allPokemonList = repository.getAllPokemon()
        }
    }

    fun addToTempList(pokemon: Pokemon){
        viewModelScope.launch {

            tempPokemonList.add(pokemon)
            _status.value = ApiResponseStatus.LOADING
            _pokemonList.value = tempPokemonList
            _status.value = ApiResponseStatus.DONE
        }

    }

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
    //flow
    /*fun searchPokemon2(characters:String):LiveData<Flow<MutableList<Pokemon>>>{
        return repository.searchPokemon(characters)
    }*/
/*
    fun searchPokemon(characters:String){
        viewModelScope.launch(Dispatchers.Main) {

            _searchPokemonList.value = repository.searchPokemon2(characters)
        }
    }*/

    fun getPokemonLikeSearch(characters:String){
        viewModelScope.launch(Dispatchers.Main) {

            try {
                _status.value = ApiResponseStatus.LOADING
                _pokemonList.value =  repository.getPokemonLikeSearch(characters)
                _status.value = ApiResponseStatus.DONE
            }catch (e:UnknownHostException){
                _status.value = ApiResponseStatus.ERROR
                //_pokemonList.value?.removeAll(mutableListOf<Pokemon>())
                Log.d(Tag,"No internet or download incomplete", e)
            }
        }}

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

