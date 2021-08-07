package com.example.growgrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.growgrow.databinding.ActivityEditProfileBinding
import com.example.growgrow.databinding.ActivitySigninBinding
import com.example.growgrow.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SignupActivity : AppCompatActivity() {

    private var _binding: ActivitySignupBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginLinkBtn.setOnClickListener {
             startActivity(Intent(this, SigninActivity::class.java))

        }

        binding.signupBtn.setOnClickListener{
            createAccount()

        }


    }

    private fun createAccount(){


        val fullname = binding.fullnameSignup.text.toString()
        val username = binding.usernameSignup.text.toString()
        val email = binding.emailLogin.text.toString()
        val password = binding.passwordLogin.text.toString()
        val confirmpassword = binding.confirmPasswordLogin.text.toString()

        when{
            TextUtils.isEmpty(fullname) -> Toast.makeText(this,"이름이 필요합니다",Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(username) -> Toast.makeText(this,"유저이름이 필요합니다",Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this,"이메일이 필요합니다",Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"비밀번호가 필요합니다",Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(confirmpassword) -> Toast.makeText(this,"비밀번호확인이 필요합니다",Toast.LENGTH_LONG).show()
            password != confirmpassword -> Toast.makeText(this,"비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show()


            else -> {

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful) {

                                saveUserInfo(fullname, username, email )

                            }
                            else
                            {

                                val message = task.exception!!.toString()
                                Toast.makeText(this,"Error: $message",Toast.LENGTH_LONG).show()


                            }

                            }
                        }



            }

        }

    private fun saveUserInfo(fullname:String, username:String, email:String){

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val db = Firebase.firestore

        val user = HashMap<String, Any>()
        user["uid"] = currentUserId
        user["fullname"] = fullname
        user["username"] = username
        user["email"] = email
        user["summary"] = "한줄요약"
        user["depart"] = "계열"
        user["major"] = "전공"
        user["region"] = "지역"

        db.collection("Users").document(currentUserId).set(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){

                        Toast.makeText(this,"가입되었습니다", Toast.LENGTH_LONG).show()

                        val intent = Intent(this@SignupActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()

                    } else {

                        val message = task.exception!!.toString()
                        Toast.makeText(this,"Error: $message",Toast.LENGTH_LONG).show()




                    }
                }






    }





    }




