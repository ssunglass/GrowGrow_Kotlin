package com.example.growgrow.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.growgrow.EditProfileActivity
import com.example.growgrow.Model.User
import com.example.growgrow.R
import com.example.growgrow.databinding.FragmentProfileBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


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
    private lateinit var keyword: String
    private lateinit var keywords: List<String>


    private var fragmentProfileBinding: FragmentProfileBinding? = null

        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?): View? {
            // Inflate the layout for this fragment
            val binding = FragmentProfileBinding.inflate(inflater, container, false)
            fragmentProfileBinding = binding

            db = FirebaseFirestore.getInstance()
            auth = FirebaseAuth.getInstance()
            userId = auth.currentUser?.uid.toString()
            userRef = db.collection("Users").document(userId)

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

            userRef.get().addOnSuccessListener { document ->
                if (document != null) {

                    val user = document.toObject<User>(User::class.java)

                    if (user != null) {

                        keywords = user.getKeywords()

                        for(keyword in keywords){

                            val chip_item_layout = layoutInflater.inflate(R.layout.chip_item, null)
                            val chip_item = chip_item_layout.findViewById<Chip>(R.id.chip_item)
                            val chip_group = binding.keywordsChip

                            chip_item.text = keyword
                            chip_item.setOnCloseIconClickListener{view ->
                                chip_group.removeView(view)
                            }
                            chip_group.addView(chip_item)

                        }


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

            binding.addKeywordBtn.setOnClickListener{
                val builder = AlertDialog.Builder(requireContext())
                val dialogView = layoutInflater.inflate(R.layout.keyword_dialog, null)
                val dialogText = dialogView.findViewById<EditText>(R.id.keyword_input)

                builder.setView(dialogView)
                        .setPositiveButton("등록", DialogInterface.OnClickListener { dialog, id ->

                            keyword = dialogText.text.toString()

                            db.collection("Users").document(userId)
                                    .update("keywords",FieldValue.arrayUnion(keyword))

                        }
                        )

                        .setNegativeButton("취소", null)

                        .create()

                        builder.setCancelable(false)

                        builder.show()





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

