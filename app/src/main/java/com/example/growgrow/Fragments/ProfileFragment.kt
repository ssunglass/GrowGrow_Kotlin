package com.example.growgrow.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.EventLog
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.growgrow.EditProfileActivity
import com.example.growgrow.Model.User
import com.example.growgrow.R
import com.example.growgrow.databinding.ActivityEditProfileBinding
import com.example.growgrow.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var userId: String
    private lateinit var userRef: DocumentReference


    private var fragmentProfileBinding: FragmentProfileBinding? = null

        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?): View? {
            // Inflate the layout for this fragment
            val binding = FragmentProfileBinding.inflate(inflater, container, false)
            fragmentProfileBinding = binding


            auth = FirebaseAuth.getInstance()
            userId = auth.currentUser?.uid.toString()
            userRef = FirebaseFirestore.getInstance()
                    .collection("Users")
                    .document(userId)

            val userRef = FirebaseFirestore.getInstance().collection("Users").document(userId)

            userRef.get().addOnSuccessListener { document ->

                if (document != null) {

                    val user = document.toObject<User>(User::class.java)

                    if (user != null) {
                        binding.fullnameProfile.text = user.getFullname()
                        binding.usernameProfile.text = user.getUsername()
                        binding.summaryProfile.text = user.getSummary()
                        binding.departProfile.text = user.getDepart()
                        binding.majorProfile.text = user.getMajor()
                    }
                }

            }

           /* userRef.addSnapshotListener(object: EventListener<DocumentSnapshot>{

                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {

                    binding.fullnameProfile.text = value?.getString("fullname")
                    binding.usernameProfile.text = value?.getString("username")



                }



            })

            */


        /*   val userRef = FirebaseFirestore.getInstance().collection("Users").document(userId)

                    userRef.get().addOnSuccessListener { document ->

                        if (document != null) {

                            val user = document.toObject<User>(User::class.java)

                            if (user != null) {
                                binding.fullnameProfile.text = user.getFullname()
                                binding.usernameProfile.text = user.getUsername()
                            }
                        }

                    }

         */


           /* userRef.addSnapshotListener( object: EventListener<DocumentSnapshot> {

                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null) {
                        Log.e("FireStore Error", error.message.toString())
                        return

                    }

                        val user = value?.toObject(User::class.java)

                        binding?.fullnameProfile?.text = user!!.getFullname()
                        binding?.usernameProfile?.text = user!!.getUsername()



                    }

                }

             ) */

            binding.editBtn.setOnClickListener {
                startActivity(Intent(context, EditProfileActivity::class.java))

            }


            return fragmentProfileBinding!!.root
        }


        override fun onDestroyView() {
            fragmentProfileBinding = null
            super.onDestroyView()
        }





        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment ProfileFragment.
             */

        }

}

