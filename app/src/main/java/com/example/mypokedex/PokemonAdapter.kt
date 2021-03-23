package com.example.mypokedex

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


            val imageId1:Int = when(pokemon.types[0]){
                "electric" -> R.drawable.electric
                "water" -> R.drawable.water
                else -> 0
            }
            binding.pokemonTypes.setImageResource(imageId1)

            var imageId2 = 0

            if (pokemon.types.size > 0){

                imageId2 = when(pokemon.types[1]){

                    "electric" -> R.drawable.electric
                    "water" -> R.drawable.water
                    else -> 0
                }
                binding.pokemonTypes2.setImageResource(imageId2)
                binding.pokemonTypes2.visibility = View.VISIBLE
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

    }
}