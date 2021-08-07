package com.example.growgrow.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.growgrow.EditProfileActivity
import com.example.growgrow.Model.User
import com.example.growgrow.R
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
    // TODO: Rename and change types of parameters

    private var fragmentProfileBinding : FragmentProfileBinding? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var profileId: String




    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        fragmentProfileBinding = binding


        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        if(pref != null) {

            this.profileId = pref.getString("profileId","none").toString()


        }


        binding.editBtn.setOnClickListener{
               startActivity(Intent(context, EditProfileActivity::class.java))

        }

       FirebaseFirestore.getInstance()
                .collection("Users").document(profileId).get().addOnSuccessListener{  document->

                    if(document != null) {

                        val user = document.toObject<User>(User::class.java)

                        binding.fullnameProfile.text = user!!.getFullname()
                        binding.usernameProfile.text = user!!.getUsername()

                    }

                }




        return  fragmentProfileBinding!!.root
    }



    override fun onDestroyView() {
        fragmentProfileBinding= null
        super.onDestroyView()
    }

    override fun onStop() {
        super.onStop()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }

    override fun onPause() {
        super.onPause()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
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