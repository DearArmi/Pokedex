package com.example.mypokedex

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.PokemonAdapter
import com.example.mypokedex.R
import com.example.mypokedex.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

lateinit var toggle: ActionBarDrawerToggle //adding button to actionbar
lateinit var binding: ActivityMainBinding
lateinit var viewModel: MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        //val navView = findViewById<NavigationView>(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        //passing toggle to drawerlayout
        binding.drawerLayout.addDrawerListener(toggle)
        //toggle ready to be used
        toggle.syncState()

        //setting arrow to toggle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Setting up Recycler LayoutManager
        val pokemonRecycler = findViewById<RecyclerView>(R.id.pokemon_recycler_list)
        pokemonRecycler.layoutManager = LinearLayoutManager(this)

        //Setting up ViewModel
        viewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)

        //Setting up adapter to Recycler
        val adapter = PokemonAdapter()
        pokemonRecycler.adapter = adapter


        viewModel.pokemonList.observe(this, Observer {

            adapter.submitList(it)
        })


        binding.navView.setNavigationItemSelectedListener {
            //clicked item
            when(it.itemId){
                R.id.kanto_option -> viewModel.load2()
                R.id.jotho_option -> Toast.makeText(this,"Jotho", Toast.LENGTH_SHORT).show()
                R.id.hoenn_option -> Toast.makeText(this,"Hoenn", Toast.LENGTH_SHORT).show()
            }//returning true because del click was handled
            binding.drawerLayout.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
/*
                pokemonList.add(Pokemon(1, "Bulbasaur"))
        pokemonList.add(Pokemon(1, "yvysur"))
        pokemonList.add(Pokemon(1, "Buaur"))
        pokemonList.add(Pokemon(1, "charmander"))
        pokemonList.add(Pokemon(1, "Bul"))

*/

