package com.example.growgrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.growgrow.databinding.ActivitySigninBinding
import com.example.growgrow.databinding.ActivitySignupBinding

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activitySignupBinding: ActivitySigninBinding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(activitySignupBinding.root)

        activitySignupBinding.signupLinkBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}