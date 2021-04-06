package com.example.mypokedex

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Pokemon")
@Parcelize
data class Pokemon(@PrimaryKey val number: Int, val name:String, val types: MutableList<String>, val image:String, val stats:IntArray/*,val hp: Int, val attack:Int, val defense:Int, val specialAttack:Int,
              val specialDefense:Int, val speed:Int*/):Parcelable {

    /*
    @PrimaryKey(autoGenerate = true)
    val number: Int*/


    enum class PokemonType{

        NORMAL, FIRE, WATER, GRASS, ELECTRIC, PSYCHIC, ICE, DRAGON, DARK, FIGHTING, FLYING,
        POISON,GROUND, ROCK, BUG, GHOST, STEEL

    }
}