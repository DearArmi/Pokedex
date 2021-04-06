package com.example.mypokedex.DataBase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    //Lists
    @TypeConverter
    fun fromList(typeList: MutableList<String>):String{

        return Gson().toJson(typeList)

    }

    @TypeConverter
    fun fromString(types: String):MutableList<String>{

        val typeList = object : TypeToken<MutableList<String>>(){}.type
        return Gson().fromJson(types, typeList)
    }

    //Arrays
    @TypeConverter
    fun fromArray(statArray: IntArray):String{

        return Gson().toJson(statArray)

    }

    @TypeConverter
    fun fromString2(stats: String):IntArray{

        val statsArray = object : TypeToken<IntArray>(){}.type
        return Gson().fromJson(stats, statsArray)
    }

}