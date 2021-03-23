package com.example.mypokedex

data class Pokemon(val number: Int, val name:String, val types: List<String>, val hp: Int, val attack:Int, val defense:Int, val specialAttack:Int,
              val specialDefense:Int, val speed:Int, val image:String) {

    enum class PokemonType{

        NORMAL, FIRE, WATER, GRASS, ELECTRIC, PSYCHIC, ICE, DRAGON, DARK, FIGHTING, FLYING,
        POISON,GROUND, ROCK, BUG, GHOST, STEEL

    }
}