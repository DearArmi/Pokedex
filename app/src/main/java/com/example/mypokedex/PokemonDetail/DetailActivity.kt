package com.example.mypokedex.PokemonDetail

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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

        setPokemonData(pokemon)

    }

    private fun setPokemonData(pokemon: Pokemon) {

        binding.detailProgressBar.visibility = View.VISIBLE
        Glide.with(this).load(pokemon.image).listener(object: RequestListener<Drawable> {

            override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
            ): Boolean {
                binding.detailProgressBar.visibility = View.GONE
                return false
            }
            override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
            ): Boolean {
                binding.detailProgressBar.visibility = View.GONE
                return false
            }
        }).error(R.drawable.image_not_supported).into(binding.detailPokemonImage)


        binding.pokemonHp.text = getString(R.string.hp, pokemon.stats[0])
        binding.pokemonAttack.text = getString(R.string.attack, pokemon.stats[1])
        binding.pokemonDefense.text = getString(R.string.defense, pokemon.stats[2])
        binding.pokemomSpeed.text = getString(R.string.speed, pokemon.stats[5])

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