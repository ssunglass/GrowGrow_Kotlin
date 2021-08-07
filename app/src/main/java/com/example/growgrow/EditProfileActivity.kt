package com.example.growgrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.growgrow.Model.User
import com.example.growgrow.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditProfileActivity : AppCompatActivity() {


    private var _binding: ActivityEditProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var region: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding =  ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        setupSpinnerRegion()
        setupSpinnerDepart()
        setupSpinnerHandler()

        userInfo()

        binding.saveEditBtn.setOnClickListener{
            updateUserInfo()

        }


        binding.logoutBtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@EditProfileActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()



        }

    }

    private fun userInfo(){

        FirebaseFirestore.getInstance()
                .collection("Users").document(firebaseUser.uid).get().addOnSuccessListener{  document->

                    if(document != null) {

                        val user = document.toObject<User>(User::class.java)

                        binding.fullnameProfile.setText(user!!.getFullname())
                        binding.usernameProfile.setText(user!!.getUsername())
                        binding.summaryProfile.setText(user!!.getSummary())
                        binding.majorProfile.setText(user!!.getMajor())

                    }

                }



    }

    private fun updateUserInfo(){

        val db = Firebase.firestore

        val user = HashMap<String, Any>()

        user["fullname"] = binding.fullnameProfile.text.toString()
        user["username"] = binding.usernameProfile.text.toString()
        user["summary"] = binding.summaryProfile.text.toString()
        user["region"] = binding.selectedRegion.text.toString()
        user["depart"] = binding.selectedDepart.text.toString()
        user["major"] = binding.majorProfile.text.toString()

        db.collection("Users").document(firebaseUser.uid).update(user)

        val intent = Intent(this@EditProfileActivity, MainActivity::class.java)
        startActivity(intent)
        finish()



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

               binding.selectedRegion.text = binding.regionSpinner.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.departSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                binding.selectedDepart.text = binding.departSpinner.getItemAtPosition(position).toString()


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