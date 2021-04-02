package com.example.mypokedex.PokemonDetail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mypokedex.Pokemon
import com.example.mypokedex.R
import com.example.mypokedex.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.stats.*


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val POKEMON_KEY = "pokemon"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras!!
        val pokemon = extras.getParcelable<Pokemon>(POKEMON_KEY)!!


           // binding.pokemonShh.text = pokemon.name.capitalize()
            //binding.pokemonHp.setVariable(1, pokemon.number)

        setupToolBar(binding, pokemon.name)


    }

    //Setting up toolbar
    private fun setupToolBar(binding: ActivityDetailBinding, pokemonName: String) {

        binding.detailToolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        binding.detailToolbar.title = pokemonName.capitalize()

        binding.detailToolbar.setNavigationOnClickListener{
            this.onBackPressed()
        }

    }


}