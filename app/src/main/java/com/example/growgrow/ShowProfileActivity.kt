package com.example.growgrow

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.Model.Bio
import com.example.growgrow.Model.User
import com.example.growgrow.databinding.ActivityShowProfileBinding
import com.example.growgrow.recyclerview.BioAdapter
import com.example.growgrow.recyclerview.OtherProfileBioAdapter
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class ShowProfileActivity : AppCompatActivity(){

    private var _binding: ActivityShowProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var userId: String
    private lateinit var profileId: String
    private lateinit var keywords: List<String>
    private lateinit var recyclerView : RecyclerView
    private lateinit var bioArrayList : ArrayList<Bio>
    private lateinit var myAdapter : OtherProfileBioAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding =  ActivityShowProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.db = FirebaseFirestore.getInstance()
        this.auth = FirebaseAuth.getInstance()
        this.userId = auth.currentUser?.uid.toString()

        recyclerView = binding.recyclerViewBioShow
        recyclerView.layoutManager = LinearLayoutManager(this)
        bioArrayList = arrayListOf()
        myAdapter = OtherProfileBioAdapter(bioArrayList)


        recyclerView.adapter = myAdapter




        if(intent.hasExtra("profileId")) {
            this.profileId = intent.getStringExtra("profileId").toString()
        }


        if( profileId == userId ) {

            binding.saveUserBtn.visibility = View.GONE

        }

        db.collection("Users")
                .document(userId)
                .collection("SavedUsers")
                .document(profileId)
                .get().addOnSuccessListener { document ->

            if(document.exists()){

                binding.saveUserBtn.text = "저장됨"

            }
        }

       db.collection("Users").document(profileId).get().addOnSuccessListener { document ->

            if (document != null) {

                val user = document.toObject<User>(User::class.java)

                if (user != null) {
                    binding.fullnameShow.text = user.getFullname()
                    binding.usernameShow.text= user.getUsername()
                    binding.departShow.text = user.getDepart()
                    binding.majorShow.text = user.getMajor()
                    binding.summaryShow.text = user.getSummary()
                }
            }

        }

        db.collection("Users").document(profileId).get().addOnSuccessListener { document ->
            if (document != null) {

                val user = document.toObject<User>(User::class.java)

                if (user != null) {

                    keywords = user.getKeywords()

                    for(keyword in keywords){

                        val chip_item_layout = layoutInflater.inflate(R.layout.chip_item_show, null)
                        val chip_item = chip_item_layout.findViewById<Chip>(R.id.chip_item_show)
                        val chip_group = binding.keywordsChipShow

                        chip_item.text = keyword

                       /* chip_item.setOnCloseIconClickListener{ view->
                            val deleted_chip = (view as Chip).text.toString()
                            db.collection("Users").document(profileId)
                                    .update("keywords", FieldValue.arrayRemove(deleted_chip))

                            chip_group.removeView(view)

                        }

                        */

                        chip_group.addView(chip_item)



                    }



                }
            }



        }

        binding.saveUserBtn.setOnClickListener {

            if(binding.saveUserBtn.text.toString() == "저장됨") {

                binding.saveUserBtn.text = "저장"
                db.collection("Users")
                        .document(userId)
                        .collection("SavedUsers")
                        .document(profileId)
                        .delete()


            } else {

                val savedUser = HashMap<String, Any>()

                savedUser["uid"] = profileId
                savedUser["fullname"] = binding.fullnameShow.text.toString()
                savedUser["username"] = binding.usernameShow.text.toString()
                savedUser["summary"] = binding.summaryShow.text.toString()
                savedUser["depart"] = binding.departShow.text.toString()
                savedUser["major"] = binding.majorShow.text.toString()


                db.collection("Users")
                        .document(userId)
                        .collection("SavedUsers")
                        .document(profileId)
                        .set(savedUser)


                binding.saveUserBtn.text = "저장됨"


            }

            Log.d("id", profileId + FirebaseAuth.getInstance().currentUser!!.uid)
        }

        EventChangeListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun EventChangeListener(){



        db = FirebaseFirestore.getInstance()
        db.collection("Users").document(profileId).collection("Bios").orderBy("date")
                .addSnapshotListener(object : EventListener<QuerySnapshot> {

                    override fun onEvent(
                            value: QuerySnapshot?,
                            error: FirebaseFirestoreException?) {

                        if (error != null) {
                            Log.e("FireStore Error", error.message.toString())
                            return

                        }

                        for (dc: DocumentChange in value?.documentChanges!!) {

                            if (dc.type == DocumentChange.Type.ADDED ) {

                                val data = dc.document.toObject(Bio::class.java)


                                bioArrayList.add(data)

                            }

                        }

                        myAdapter.notifyDataSetChanged()

                    }


                })




    }

}

