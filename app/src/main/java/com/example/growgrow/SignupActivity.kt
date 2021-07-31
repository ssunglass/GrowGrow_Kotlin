package com.example.growgrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.growgrow.databinding.ActivitySigninBinding
import com.example.growgrow.databinding.ActivitySignupBinding


class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activitySignUpBinding: ActivitySignupBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(activitySignUpBinding.root)

        activitySignUpBinding.loginLinkBtn.setOnClickListener {
             startActivity(Intent(this, SigninActivity::class.java))



        }


    }
}