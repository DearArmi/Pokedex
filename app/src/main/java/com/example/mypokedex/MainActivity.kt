package com.example.mypokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

lateinit var toggle: ActionBarDrawerToggle //adding button to actionbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        //passion toggle to drawerlayout
        drawerLayout.addDrawerListener(toggle)
        //toggle ready to be used
        toggle.syncState()

        //setting arrow to toggle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
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

