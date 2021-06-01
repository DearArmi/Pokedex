package com.example.mypokedex.main

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mypokedex.Api.ApiResponseStatus
import com.example.mypokedex.Pokemon
import com.example.mypokedex.PokemonAdapter

import com.example.mypokedex.PokemonDetail.DetailActivity
import com.example.mypokedex.R

import com.example.mypokedex.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity()/*, SearchView.OnQueryTextListener*/ {

    private lateinit var toggle: ActionBarDrawerToggle //adding button to actionbar
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toggle = ActionBarDrawerToggle()

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.mainToolbar,
            R.string.open,
            R.string.close
        )
        //passing toggle to drawerlayout
        binding.drawerLayout.addDrawerListener(toggle)
        //toggle ready to be used
        toggle.syncState()
        //TODO---FIX THIS
        //setting up arrow to toggle
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Setting up Recycler LayoutManager
        binding.pokemonRecyclerList.layoutManager = LinearLayoutManager(this)

        //Setting up ViewModel
        viewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)

        //Setting up adapter to Recycler
        adapter = PokemonAdapter()
        binding.pokemonRecyclerList.adapter = adapter


        viewModel.pokemonList.observe(this, Observer {

            adapter.submitList(it)
            handleEmptyView(it, binding)
        })


        viewModel.status.observe(this, Observer {

            when(it){
                ApiResponseStatus.DONE -> {binding.mainProgressbar.visibility = View.GONE
                                            /*binding.mainToolbar.setEnabled(true)*/}
                ApiResponseStatus.LOADING -> {binding.mainProgressbar.visibility = View.VISIBLE
                                                /*binding.mainToolbar.setEnabled(false)*/}
                ApiResponseStatus.ERROR -> {binding.mainProgressbar.visibility = View.GONE
                                            /*binding.mainToolbar.setEnabled(true)*/
                                            Toast.makeText(this,"No Internet, Download Incomplete", Toast.LENGTH_SHORT).show() }
            }

        })


        binding.navView.setNavigationItemSelectedListener {
            //clicked item
            when(it.itemId){
                R.id.kanto_option -> {viewModel.getByRegion(1, 151,151)
                                        Toast.makeText(this, "Kanto Region Selected", Toast.LENGTH_SHORT).show()}
                R.id.jotho_option -> {viewModel.getByRegion(152, 251,100)
                                        Toast.makeText(this, "Jhoto Region Selected", Toast.LENGTH_SHORT).show()}
                R.id.hoenn_option -> {viewModel.getByRegion(252, 386,135)
                                        Toast.makeText(this, "Hoenn Region Selected", Toast.LENGTH_SHORT).show()}
                R.id.sinnoh_option -> {viewModel.getByRegion(387, 493,107)
                                        Toast.makeText(this, "Sinnoh Region Selected", Toast.LENGTH_SHORT).show()}
                R.id.unova_option -> {viewModel.getByRegion(494, 649,156)
                                        Toast.makeText(this, "Unova Region Selected", Toast.LENGTH_SHORT).show()}
                R.id.kalos_option -> {viewModel.getByRegion(650, 721,72)
                                        Toast.makeText(this, "Kalos Region Selected", Toast.LENGTH_SHORT).show()}
                R.id.alola_option -> {viewModel.getByRegion(722, 809,88)
                                        Toast.makeText(this, "Alola Region Selected", Toast.LENGTH_SHORT).show()}
                R.id.galar_option -> {viewModel.getByRegion(810, 898,89)
                                        Toast.makeText(this, "Galar Region Selected", Toast.LENGTH_SHORT).show()}
                R.id.delete -> {viewModel.delete()
                                Toast.makeText(this, "ya", Toast.LENGTH_SHORT).show()}
            }//returning true because del click was handled
            binding.drawerLayout.closeDrawers()
            touchSound()
            true
        }

        adapter.onItemClickListener = {

            openPokemonDetailActivity(it)
            touchSound()
        }

    }

    private fun handleEmptyView(pokemonList: MutableList<Pokemon>, binding: ActivityMainBinding) {

            if(pokemonList.isEmpty()){
                binding.emptyText.visibility = View.VISIBLE
            }else{
                binding.emptyText.visibility = View.GONE
            }

    }

    private fun openPokemonDetailActivity(pokemon: Pokemon) {

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.POKEMON_KEY, pokemon)
        startActivity(intent)

    }

    ////Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.options, menu)
        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true

        ///Search
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                /*viewModel.tempPokemonList.clear()
                val searchText = query!!.toLowerCase(Locale.getDefault())

                if (searchText.isNotEmpty()){

                    viewModel.allPokemonList.forEach {

                        if (it.name.toLowerCase(Locale.getDefault()).contains(searchText)){

                            //viewModel.tempPokemonList.add(it)
                            viewModel.addToTempList(it)

                        }
                    }
                    //viewModel.tempPokemonList.clear()
                    adapter.notifyDataSetChanged()
                }*/
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                viewModel.tempPokemonList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())

                if (searchText.isNotEmpty()){

                    viewModel.allPokemonList.forEach {

                        if (it.name.toLowerCase(Locale.getDefault()).contains(searchText)){

                            viewModel.addToTempList(it)

                        }
                    }
                    adapter.notifyDataSetChanged()
                }
                return false
            }
        })
        return true
    }
    ///Options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private fun searchPokemon(pokemonName: String){

        //flow
        /*
        val query = "%$pokemonName%"
        viewModel.searchPokemon2(pokemonName).observe(this, Observer { mutableList ->

            mutableList.let {
                adapter.su
            }
            //adapter.submitList(it)
        })*/
        /*viewModel.searchPokemon(pokemonName).observe(this, Observer {
            adapter.submitList(it)
        })*/

    }
//////////
    private fun touchSound(){

        val sound = MediaPlayer.create(this, R.raw.sound_effect)
        sound.start()

    }
}

