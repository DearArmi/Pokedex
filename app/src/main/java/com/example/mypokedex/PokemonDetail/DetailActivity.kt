package com.example.mypokedex.PokemonDetail

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
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


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var viewModel: DetailViewModel
    private var imageViewsList = mutableListOf<ImageView>()

    companion object {
        const val POKEMON_KEY = "pokemon"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
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

         //TODO with include in layout----LISTOOOOOOOOOOOOOOOOO
        imageViewsList.add(binding.effectivenessInclude.weak1)
        imageViewsList.add(binding.effectivenessInclude.weak2)
        imageViewsList.add(binding.effectivenessInclude.weak3)
        imageViewsList.add(binding.effectivenessInclude.weak4)
        imageViewsList.add(binding.effectivenessInclude.weak5)
        imageViewsList.add(binding.effectivenessInclude.weak6)
        imageViewsList.add(binding.effectivenessInclude.weak7)
        imageViewsList.add(binding.effectivenessInclude.strong1)
        imageViewsList.add(binding.effectivenessInclude.strong2)
        imageViewsList.add(binding.effectivenessInclude.strong3)
        imageViewsList.add(binding.effectivenessInclude.strong4)
        imageViewsList.add(binding.effectivenessInclude.strong5)
        imageViewsList.add(binding.effectivenessInclude.strong6)
        imageViewsList.add(binding.effectivenessInclude.strong7)
        imageViewsList.add(binding.effectivenessInclude.halfFrom1)
        imageViewsList.add(binding.effectivenessInclude.halfFrom2)
        imageViewsList.add(binding.effectivenessInclude.halfFrom3)
        imageViewsList.add(binding.effectivenessInclude.halfFrom4)
        imageViewsList.add(binding.effectivenessInclude.halfFrom5)
        imageViewsList.add(binding.effectivenessInclude.halfFrom6)
        imageViewsList.add(binding.effectivenessInclude.halfFrom7)
        imageViewsList.add(binding.effectivenessInclude.halfTo1)
        imageViewsList.add(binding.effectivenessInclude.halfTo2)
        imageViewsList.add(binding.effectivenessInclude.halfTo3)
        imageViewsList.add(binding.effectivenessInclude.halfTo4)
        imageViewsList.add(binding.effectivenessInclude.halfTo5)
        imageViewsList.add(binding.effectivenessInclude.halfTo6)
        imageViewsList.add(binding.effectivenessInclude.halfTo7)
        imageViewsList.add(binding.effectivenessInclude.noFrom1)
        imageViewsList.add(binding.effectivenessInclude.noFrom2)
        imageViewsList.add(binding.effectivenessInclude.noTo1)
        imageViewsList.add(binding.effectivenessInclude.noTo2)
        
        


        binding.floatingBt.setOnClickListener {

            val mediaPlayer = MediaPlayer.create(this, R.raw.sound_effect)
            mediaPlayer.start()
        }
        //TODO---There's a problem here
       viewModel.pokemonEffectivenessList.observe(this, Observer {
           val effectivenessBigList = it[0]
           val doubleDamageFrom = effectivenessBigList[0]
           //var index = 0
           //binding.doubleDamageFrom.text = doubleDamageFromTo[0] + doubleDamageFromTo[1] + doubleDamageFromTo[2] + doubleDamageFromTo[3]
           setTypeImages(0, doubleDamageFrom, imageViewsList)

           val doubleDamageTo = effectivenessBigList[1]
           setTypeImages(7, doubleDamageTo, imageViewsList)

           val halfDamageFrom = effectivenessBigList[2]
           setTypeImages(14, halfDamageFrom, imageViewsList)

           val halfDamageTo = effectivenessBigList[3]
           setTypeImages(21, halfDamageTo, imageViewsList)

           val noDamageFrom = effectivenessBigList[4]
           setTypeImages(28, noDamageFrom, imageViewsList)

           val noDamageTo = effectivenessBigList[5]
           setTypeImages(30, noDamageTo, imageViewsList)
       })


    }
    //TODO---There's a problem here
    private fun setTypeImages(beginIndex:Int, effectivenessArray: Array<String>, imageViewArray:MutableList<ImageView>) {

        //var position = 0

        for (i in 0 until effectivenessArray.size){
            when(effectivenessArray[i]){
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