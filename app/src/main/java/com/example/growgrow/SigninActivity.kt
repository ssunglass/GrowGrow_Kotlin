package com.example.growgrow

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.growgrow.databinding.ActivityEditProfileBinding
import com.example.growgrow.databinding.ActivitySigninBinding
import com.example.growgrow.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SigninActivity : AppCompatActivity() {

    private var _binding: ActivitySigninBinding? = null
    private val binding get() = _binding!!
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val user = auth.currentUser





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                var deepLink: Uri? = null

                if(pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                val user = Firebase.auth.currentUser

                if (user == null &&
                    deepLink != null &&
                    deepLink.getBooleanQueryParameter("invitedby", false)) {
                    val referrerUid = deepLink.getQueryParameter("invitedby")
                    createAnonymousAccountWithReferrerInfo(referrerUid)

                }




            }

      /*  binding.signupLinkBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

       */


        binding.loginBtn.setOnClickListener {
            loginUser()
        }
    }


    private fun createAnonymousAccountWithReferrerInfo(referrerUid: String?) {


        Firebase.auth
            .signInAnonymously()
            .addOnSuccessListener {
                val user = Firebase.auth.currentUser
                val userRecord = FirebaseFirestore.getInstance().collection("Users")
                    .document(user!!.uid)

                val referred = HashMap<String, Any>()

                referred["referredBy"] = referrerUid.toString()

                userRecord.set(referred)


            }.addOnCompleteListener{

                if(it.isComplete){
                    startActivity(Intent(this, SignupActivity::class.java))

                }

            }

    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkAvailable(): Boolean{
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)

        return (capabilities != null && capabilities.hasCapability(NET_CAPABILITY_INTERNET))

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


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()


        if(isNetworkAvailable()){

            if( user!= null ){

                if(user.isEmailVerified){

                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()


                }

            }


        } else {
            binding.mainSigninLayout.visibility = View.GONE
            binding.disconnectedLayout.visibility = View.VISIBLE




        }
    }


}