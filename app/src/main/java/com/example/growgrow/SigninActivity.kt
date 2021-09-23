package com.example.growgrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.growgrow.databinding.ActivityEditProfileBinding
import com.example.growgrow.databinding.ActivitySigninBinding
import com.example.growgrow.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SigninActivity : AppCompatActivity() {

    private var _binding: ActivitySigninBinding? = null
    private val binding get() = _binding!!
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val user = auth.currentUser





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupLinkBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            loginUser()
        }
    }


    private fun loginUser() {

         val email = binding.emailLogin.text.toString()
         val password = binding.passwordLogin.text.toString()

        when{

            TextUtils.isEmpty(email) -> Toast.makeText(this,"이메일이 필요합니다", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"비밀번호가 필요합니다", Toast.LENGTH_LONG).show()



            else -> {

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                    if(task.isSuccessful) {

                        if(mAuth.currentUser!!.isEmailVerified){

                            Toast.makeText(this,"로그인되었습니다", Toast.LENGTH_LONG).show()

                            val intent = Intent(this@SigninActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()


                        } else {

                            val intent = Intent(this@SigninActivity, EmailVerifyActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()


                        }




                    }
                }



            }

        }

    }

    override fun onStart() {
        super.onStart()
        if( user!= null ){

            if(user.isEmailVerified){

                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()


            }
            

        }
    }


}