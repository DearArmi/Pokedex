package com.example.mypokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypokedex.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

lateinit var toggle: ActionBarDrawerToggle //adding button to actionbar
lateinit var binding: ActivityMainBinding

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

        binding.pokemonRecyclerList.layoutManager = LinearLayoutManager(this)
        val adapter = PokemonAdapter()
        binding.pokemonRecyclerList.adapter = adapter
        val listType = listOf<String>("water", "electric")
        val listType2 = listOf<String>("water")
        val pokemonList = mutableListOf<Pokemon>()
        pokemonList.add(Pokemon(1, "Bulbasaur", listType, 1,1,1,1,1,1,""))
        pokemonList.add(Pokemon(1, "yvysur", listType2, 1,1,1,1,1,1,""))
        pokemonList.add(Pokemon(1, "Buaur", listType, 1,1,1,1,1,1,""))
        pokemonList.add(Pokemon(1, "charmander", listType, 1,1,1,1,1,1,""))
        pokemonList.add(Pokemon(1, "Bul", listType2, 1,1,1,1,1,1,""))

        adapter.submitList(pokemonList)

        //val pokemon = Pokemon(1, "Bulbasaur", listType, 1,1,1,1,1,1,"")
        //setting arrow to toggle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            //clicked item
            when(it.itemId){
                R.id.kanto_option -> Toast.makeText(this,"Kanto", Toast.LENGTH_SHORT).show()
                R.id.jotho_option -> Toast.makeText(this,"Jotho", Toast.LENGTH_SHORT).show()
                R.id.hoenn_option -> Toast.makeText(this,"Hoenn", Toast.LENGTH_SHORT).show()
            }//returning true because del click was handled
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

