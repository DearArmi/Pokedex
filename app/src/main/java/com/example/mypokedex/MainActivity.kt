package com.example.mypokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.PokemonAdapter
import com.example.mypokedex.PokemonDetail.DetailActivity
import com.example.mypokedex.R
import com.example.mypokedex.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle //adding button to actionbar
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toggle = ActionBarDrawerToggle()

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.mainToolbar, R.string.open, R.string.close)
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
        val adapter = PokemonAdapter()
        binding.pokemonRecyclerList.adapter = adapter


        viewModel.pokemonList.observe(this, Observer {

            adapter.submitList(it)
        })


        binding.navView.setNavigationItemSelectedListener {
            //clicked item
            when(it.itemId){
                R.id.kanto_option -> {viewModel.getByRegion(1, 151)
                                        Toast.makeText(this, "Kanto Region Selected", Toast.LENGTH_SHORT).show()}
                R.id.jotho_option -> {viewModel.getByRegion(152, 251)
                                        Toast.makeText(this, "Jhoto Region Selected", Toast.LENGTH_SHORT).show()}
                R.id.hoenn_option -> {viewModel.getByRegion(252, 386)
                                        Toast.makeText(this, "Hoenn Region Selected", Toast.LENGTH_SHORT).show()}
            }//returning true because del click was handled
            binding.drawerLayout.closeDrawers()
            true
        }

        adapter.onItemClickListener = {

            openPokemonDetailActivity(it)

        }

    }


    private fun openPokemonDetailActivity(pokemon: Pokemon) {

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.POKEMON_KEY, pokemon)
        startActivity(intent)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

