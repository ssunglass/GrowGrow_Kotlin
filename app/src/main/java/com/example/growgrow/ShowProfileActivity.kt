package com.example.growgrow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.growgrow.databinding.ActivityEditProfileBinding
import com.example.growgrow.databinding.ActivityShowProfileBinding

class ShowProfileActivity : AppCompatActivity() {

    private var _binding: ActivityShowProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding =  ActivityShowProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}