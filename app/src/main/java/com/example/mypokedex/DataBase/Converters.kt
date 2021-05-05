package com.example.mypokedex.DataBase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream

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

    //Bitmap
    @TypeConverter
    fun fromBitmap(bitmap:Bitmap):ByteArray{

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

        return  outputStream.toByteArray()
    }

    @TypeConverter
    fun fromByteArray(byteArray: ByteArray):Bitmap{

        return  BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}