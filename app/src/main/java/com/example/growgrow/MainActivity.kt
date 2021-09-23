package com.example.growgrow

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
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

   // val auth = FirebaseAuth.getInstance()
   // val user = auth.currentUser



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

        val navView: NavigationBarView = findViewById(R.id.nav_view)



        navView.setOnItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(HomeFragment())


    }


    /*
    override fun onStart() {
        super.onStart()

       if (user != null && user.isEmailVerified) {
            val intent = Intent(this, SigninActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }


    }

       */

    private fun moveToFragment(fragment: Fragment)
    {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()

    }

}