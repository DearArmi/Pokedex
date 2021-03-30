package com.example.mypokedex.DataBase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromList(typeList: MutableList<String>):String{

        return Gson().toJson(typeList)

    }

    @TypeConverter
    fun fromString(types: String):MutableList<String>{

        val typeList = object : TypeToken<MutableList<String>>(){}.type
        return Gson().fromJson(types, typeList)
    }

}