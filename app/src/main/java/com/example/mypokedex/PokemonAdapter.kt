package com.example.mypokedex

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.contains
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mypokedex.databinding.PokemonList2Binding
import com.example.mypokedex.databinding.PokemonListBinding
import java.util.*

//lateinit var  onItemClickListener: (Pokemon) -> Unit

class PokemonAdapter: ListAdapter<Pokemon, PokemonAdapter.ViewHolder>(Diffcallback) {


    companion object Diffcallback: DiffUtil.ItemCallback<Pokemon>(){

        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return  oldItem == newItem
        }

    }

    lateinit var  onItemClickListener: (Pokemon) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonAdapter.ViewHolder {

        val binding = PokemonList2Binding.inflate(LayoutInflater.from(parent.context))
        return  ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: PokemonAdapter.ViewHolder, position: Int) {

        val pokemon = getItem(position)
        holder.bind(pokemon)

    }

    inner class ViewHolder(private val binding:PokemonList2Binding): RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(pokemon: Pokemon){

            binding.pokemonNumber.text = "#" + pokemon.number.toString()
            binding.pokemonName.text = pokemon.name.capitalize()
            
            binding.miniPokemonImage.load(pokemon.pokemonPreview)

            //TODO Find a way to paint just one type and not assigning empty image for those pokemon that has one type (Array might be a solution)
            if(pokemon.types.size >1){

                binding.pokemonTypes.setImageResource(oneOrTwoTypes(pokemon.types)[0])
                binding.pokemonTypes2.setImageResource(oneOrTwoTypes(pokemon.types)[1])
            }/*else{

                binding.pokemonTypes.setImageResource(oneOrTwoTypes(pokemon.types)[0])
                //binding.pokemonTypes2.visibility = View.INVISIBLE

            }*/

            binding.executePendingBindings()

            binding.root.setOnClickListener{

                if (::onItemClickListener.isInitialized){

                    onItemClickListener(pokemon)
                }else{
                    Log.e("EqAdapter", "Lampda not Initialized")
                }

            }
        }
        //Assigning pokemon image type depending on how many types that pokemon has
        private fun oneOrTwoTypes(listTypes:MutableList<String>):MutableList<Int>{

            //val array = IntArray(2) Esta puede ser la solucion a lo de arriba
            val imageListId = mutableListOf<Int>(0,0)
            var position = 0
            while (position < listTypes.size){

                imageListId[position] = when(listTypes[position]){
                    "bug" -> R.drawable.bug
                    "dark" -> R.drawable.dark
                    "dragon" -> R.drawable.dragon
                    "electric" -> R.drawable.electric
                    "fairy" -> R.drawable.fairy
                    "fire" -> R.drawable.fire
                    "fighting" -> R.drawable.fighting
                    "flying" -> R.drawable.flying
                    "ghost" -> R.drawable.ghost
                    "grass" -> R.drawable.grass
                    "ground" -> R.drawable.ground
                    "ice" -> R.drawable.ice
                    "normal" -> R.drawable.normal
                    "poison" -> R.drawable.poison
                    "psychic" -> R.drawable.psychic
                    "rock" -> R.drawable.rock
                    "steel" -> R.drawable.steel
                    "water" -> R.drawable.water
                    else -> R.drawable.nothing

                }
                position++
            }
            return imageListId
        }
    }
}