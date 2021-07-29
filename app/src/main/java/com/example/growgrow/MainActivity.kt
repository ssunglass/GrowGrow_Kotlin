package com.example.growgrow

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.growgrow.Fragments.HomeFragment
import com.example.growgrow.Fragments.ProfileFragment
import com.example.growgrow.Fragments.SearchFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    internal var selectedFragment: Fragment? = null



    private val onNavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener{ item ->
        when (item.itemId) {
            R.id.nav_home -> {
                selectedFragment = HomeFragment()
            }
            R.id.nav_search -> {


                selectedFragment = SearchFragment()
            }
            R.id.nav_profile -> {


                selectedFragment = ProfileFragment()
            }

        }
            if(selectedFragment != null) {
                supportFragmentManager
                        .beginTransaction()
                        .replace(
                                R.id.fragment_container,
                                selectedFragment!!
                        ).commit()
        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: NavigationBarView = findViewById(R.id.nav_view)
        navView.setOnItemSelectedListener(onNavigationItemSelectedListener)

        supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.fragment_container,
                        HomeFragment()

                ).commit()

    }
}