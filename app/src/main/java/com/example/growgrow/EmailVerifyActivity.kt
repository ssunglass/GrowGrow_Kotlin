package com.example.growgrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.growgrow.databinding.ActivityEmailVerifyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class EmailVerifyActivity : AppCompatActivity() {

    private var _binding: ActivityEmailVerifyBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding =  ActivityEmailVerifyBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!


        setContentView(binding.root)






    }

    override fun onStart() {
        super.onStart()

        currentUser.reload().addOnCompleteListener { task ->
            if(task.isSuccessful){

                if(currentUser.isEmailVerified){

                    Toast.makeText(this,"인증되었습니다",Toast.LENGTH_LONG).show()

                    val intent = Intent(this@EmailVerifyActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()



                }


            }
        }


    }
}