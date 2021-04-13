package com.example.mypokedex

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.Api.ApiResponseStatus
import com.example.mypokedex.DataBase.getDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

private val Tag = MainViewModel::class.java.simpleName

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val dataBase = getDataBase(application)
    private val repository = MainRepository(dataBase)

    private var _pokemonList = MutableLiveData<MutableList<Pokemon>>()
    val pokemonList: LiveData<MutableList<Pokemon>>
        get() = _pokemonList

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

    fun getByRegion(valueOne:Int, valueTwo:Int){
        viewModelScope.launch(Dispatchers.Main) {

            _status.value = ApiResponseStatus.LOADING
            _pokemonList.value = repository.getRegion(valueOne, valueTwo)
            _status.value = ApiResponseStatus.DONE
        }}

}

