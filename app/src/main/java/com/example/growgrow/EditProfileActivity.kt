package com.example.growgrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.growgrow.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth

class EditProfileActivity : AppCompatActivity() {

    private var _binding: ActivityEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding =  ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinnerRegion()
        setupSpinnerDepart()
        setupSpinnerHandler()


        binding.logoutBtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@EditProfileActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()



        }

    }

    private fun setupSpinnerRegion(){
        val region = resources.getStringArray(R.array.region_array)
        val adapter = ArrayAdapter(this , R.layout.support_simple_spinner_dropdown_item, region)

        binding.regionSpinner.adapter = adapter



    }

    private fun setupSpinnerDepart(){
        val depart = resources.getStringArray(R.array.depart_array)
        val adapter = ArrayAdapter(this , R.layout.support_simple_spinner_dropdown_item, depart)

        binding.departSpinner.adapter = adapter



    }

    private fun setupSpinnerHandler() {
        binding.regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.departSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }







}