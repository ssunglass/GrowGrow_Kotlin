package com.example.growgrow

import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {



    private val onNavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener{ item ->
        when (item.itemId) {
            R.id.nav_home -> {
                moveToFragment(HomeFragment())
                return@OnItemSelectedListener true

            }
            R.id.nav_search -> {

                moveToFragment(SearchFragment())

                return@OnItemSelectedListener true


            }
            R.id.nav_profile -> {

                moveToFragment(ProfileFragment())

                return@OnItemSelectedListener true



            }

        }


        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = FirebaseAuth.getInstance()
        val navView: NavigationBarView = findViewById(R.id.nav_view)

        if (auth.currentUser == null) {
            val intent = Intent(this, SigninActivity::class.java);
            startActivity(intent)
            finish()
        }

        navView.setOnItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(HomeFragment())


    }

    private fun moveToFragment(fragment: Fragment)
    {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()

    }

}