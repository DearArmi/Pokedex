package com.example.mypokedex.PokemonDetail

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.mypokedex.Pokemon
import com.example.mypokedex.R
import com.example.mypokedex.databinding.ActivityDetailBinding
import com.example.mypokedex.databinding.TypeEffectivenessBinding
import com.example.mypokedex.databinding.TypeEffectivenessBindingImpl


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    //private lateinit var effectivenessBinding: TypeEffectivenessBinding
    private lateinit var viewModel: DetailViewModel
    private var imageViewsArray = mutableListOf<ImageView>()

    companion object {
        const val POKEMON_KEY = "pokemon"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        //effectivenessBinding = TypeEffectivenessBinding.inflate(layoutInflater)
        //effectivenessBinding = TypeEffectivenessBinding.inflate(layoutInflater, effectivenessBinding.este, true)
        setContentView(binding.root)

        //Getting extras
        val extras = intent.extras!!
        val pokemon = extras.getParcelable<Pokemon>(POKEMON_KEY)!!

        //Setting up ViewModel
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        //Setting Up toolbar
        setupToolBar(binding, pokemon.name)
        //Setting up pokemon info
        setPokemonData(pokemon)

        /* TODO with include in layout
        imageViewsArray.add(effectivenessBinding.weak1)
        imageViewsArray.add(effectivenessBinding.weak2)
        imageViewsArray.add(effectivenessBinding.weak3)
        imageViewsArray.add(effectivenessBinding.weak4)
        imageViewsArray.add(effectivenessBinding.weak5)
        imageViewsArray.add(effectivenessBinding.weak6)
        imageViewsArray.add(effectivenessBinding.weak7)
        imageViewsArray.add(effectivenessBinding.strong1)
        imageViewsArray.add(effectivenessBinding.strong2)
        imageViewsArray.add(effectivenessBinding.strong3)
        imageViewsArray.add(effectivenessBinding.strong4)
        imageViewsArray.add(effectivenessBinding.strong5)
        imageViewsArray.add(effectivenessBinding.strong6)
        imageViewsArray.add(effectivenessBinding.strong7)*/

        imageViewsArray.add(binding.weak1)
        imageViewsArray.add(binding.weak2)
        imageViewsArray.add(binding.weak3)
        imageViewsArray.add(binding.weak4)
        imageViewsArray.add(binding.weak5)
        imageViewsArray.add(binding.weak6)
        imageViewsArray.add(binding.weak7)
        imageViewsArray.add(binding.strong1)
        imageViewsArray.add(binding.strong2)
        imageViewsArray.add(binding.strong3)
        imageViewsArray.add(binding.strong4)
        imageViewsArray.add(binding.strong5)
        imageViewsArray.add(binding.strong6)
        imageViewsArray.add(binding.strong7)

        binding.floatingBt.setOnClickListener {

            val mediaPlayer = MediaPlayer.create(this, R.raw.sound_effect)
            mediaPlayer.start()
        }

       viewModel.pokemonEffectivenessList.observe(this, Observer {
           val doubleDamageFromBigList = it[0]
           val doubleDamageFromTo = doubleDamageFromBigList[0]
           //var index = 0
           //binding.doubleDamageFrom.text = doubleDamageFromTo[0] + doubleDamageFromTo[1] + doubleDamageFromTo[2] + doubleDamageFromTo[3]
           setTypeImages(0, doubleDamageFromTo, imageViewsArray)

           //val doubleDamageFrom2 = it[0]
           val doubleDamageFromTo2 = doubleDamageFromBigList[1]
           //index = 7
           ///binding.doubleDamageTo.text = doubleDamageFromTo2[0] + doubleDamageFromTo2[1] + doubleDamageFromTo2[2] + doubleDamageFromTo2[3]
           setTypeImages(7, doubleDamageFromTo2, imageViewsArray)
       })
    }

    private fun setTypeImages(beginIndex:Int, doubleDamageFromTo: Array<String>, imageViewArray:MutableList<ImageView>) {

        //var position = 0

        for (i in 0 until doubleDamageFromTo.size){
            when(doubleDamageFromTo[i]){
                "bug" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.bug)
                "dark" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.dark)
                "dragon" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.dragon)
                "electric" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.electric)
                "fairy" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.fairy)
                "fire" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.fire)
                "fighting" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.fighting)
                "flying" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.flying)
                "ghost" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.ghost)
                "grass" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.grass)
                "ground" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.ground)
                "ice" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.ice)
                "normal" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.normal)
                "poison" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.poison)
                "psychic" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.psychic)
                "rock" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.rock)
                "steel" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.steel)
                "water" -> imageViewArray[beginIndex+i].setImageResource(R.drawable.water)
                else -> R.drawable.nothing

            }

        }

    }

    private fun setPokemonData(pokemon: Pokemon) {

        //Downloading Pokemon Picture
        binding.detailProgressBar.visibility = View.VISIBLE
        Glide.with(this).load(pokemon.image).listener(object: RequestListener<Drawable> {

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.detailProgressBar.visibility = View.GONE
                return false
            }
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                binding.detailProgressBar.visibility = View.GONE
                return false
            }
        }).error(R.drawable.image_not_supported).into(binding.detailPokemonImage)


        binding.pokemonHp.text = getString(R.string.hp, pokemon.stats[0])
        binding.pokemonAttack.text = getString(R.string.attack, pokemon.stats[1])
        binding.pokemonDefense.text = getString(R.string.defense, pokemon.stats[2])
        binding.pokemonSa.text = getString(R.string.sa, pokemon.stats[3])
        binding.pokemonSd.text = getString(R.string.sd, pokemon.stats[4])
        binding.pokemomSpeed.text = getString(R.string.speed, pokemon.stats[5])
        viewModel.loadTypeEffectiveness(pokemon.types)


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