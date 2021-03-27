package com.example.mypokedex

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.databinding.PokemonListBinding

class PokemonAdapter: ListAdapter<Pokemon, PokemonAdapter.ViewHolder>(Diffcallback) {


    companion object Diffcallback: DiffUtil.ItemCallback<Pokemon>(){

        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return  oldItem == newItem
        }

    }

    //lateinit var  onItemClickListener: (Pokemon) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonAdapter.ViewHolder {

        val binding = PokemonListBinding.inflate(LayoutInflater.from(parent.context))
        return  ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: PokemonAdapter.ViewHolder, position: Int) {

        val pokemon = getItem(position)
        holder.bind(pokemon)

    }
    //Se coloca inner class para lateinit var  onItemClickListener: sirva dentro del adapter
    inner class ViewHolder(private val binding:PokemonListBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: Pokemon){

            binding.pokemonNumber.text = pokemon.number.toString()
            binding.pokemonName.text = pokemon.name

            if(pokemon.types.size >1){

                binding.pokemonTypes.setImageResource(oneOrTwoTypes(pokemon.types)[0])
                binding.pokemonTypes2.setImageResource(oneOrTwoTypes(pokemon.types)[1])
            }else{

                binding.pokemonTypes.setImageResource(oneOrTwoTypes(pokemon.types)[0])
                binding.pokemonTypes2.visibility = View.INVISIBLE

            }

            binding.executePendingBindings()

            /*binding.root.setOnClickListener{

                if (::onItemClickListener.isInitialized){

                    onItemClickListener(pokemon)
                }else{
                    Log.e("EqAdapter", "Lampda not Initialized")
                }

            }*/
        }
        //Assigning pokemon image type depending on how many types that pokemon has
        private fun oneOrTwoTypes(listTypes:MutableList<String>):MutableList<Int>{

            val imageListId = mutableListOf<Int>(0,0)
            var i = 0
            while (i < listTypes.size){

                imageListId[i] = when(listTypes[i]){
                    "electric" -> R.drawable.electric
                    "water" -> R.drawable.water
                    else -> 0

                }
                i++
            }
            return imageListId
        }
    }
}