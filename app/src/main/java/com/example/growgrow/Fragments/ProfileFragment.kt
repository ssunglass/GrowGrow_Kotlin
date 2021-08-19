package com.example.growgrow.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.AddBioActivity
import com.example.growgrow.EditProfileActivity
import com.example.growgrow.Model.Bio
import com.example.growgrow.Model.User
import com.example.growgrow.R
import com.example.growgrow.SwipeGesture
import com.example.growgrow.databinding.FragmentProfileBinding
import com.example.growgrow.recyclerview.BioAdapter
import com.example.growgrow.recyclerview.UserAdapter
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.time.Year
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(),BioAdapter.MyOnClickListener {
    private lateinit var recyclerView : RecyclerView
    private lateinit var bioArrayList : ArrayList<Bio>
    private lateinit var myAdapter : BioAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var userId: String
    private lateinit var userRef: DocumentReference
    private lateinit var keyword: String
    private lateinit var description: String
    private lateinit var date: String
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


            recyclerView = binding.recyclerViewBio
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            bioArrayList = arrayListOf()
            myAdapter = BioAdapter(bioArrayList, this@ProfileFragment)


            recyclerView.adapter = myAdapter

            val swipeGesture = object : SwipeGesture(requireContext()){

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    when(direction) {
                        ItemTouchHelper.LEFT -> {

                            userRef
                                .collection("Bios")
                                .document(bioArrayList[viewHolder.adapterPosition].getDate())
                                .delete()


                            myAdapter.deleteItem(viewHolder.adapterPosition)





                        }

                    }


                }
            }

            val touchHelper = ItemTouchHelper(swipeGesture)
            touchHelper.attachToRecyclerView(recyclerView)





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

                            chip_item.setOnCloseIconClickListener{ view->
                                val deleted_chip = (view as Chip).text.toString()
                                db.collection("Users").document(userId)
                                        .update("keywords", FieldValue.arrayRemove(deleted_chip))

                                chip_group.removeView(view)



                            }

                            chip_group.addView(chip_item)



                        }



                    }
                }





            }


            EventChangeListener()





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

                val chip_item_layout = layoutInflater.inflate(R.layout.chip_item, null)
                val chip_item = chip_item_layout.findViewById<Chip>(R.id.chip_item)
                val chip_group = binding.keywordsChip

                builder.setView(dialogView)
                        .setPositiveButton("등록", DialogInterface.OnClickListener { dialog, id ->

                            keyword = dialogText.text.toString()

                            db.collection("Users").document(userId)
                                    .update("keywords", FieldValue.arrayUnion(keyword))

                            chip_item.text = keyword

                            chip_item.setOnCloseIconClickListener{ view->
                                val deleted_chip = (view as Chip).text.toString()
                                db.collection("Users").document(userId)
                                        .update("keywords", FieldValue.arrayRemove(deleted_chip))

                                chip_group.removeView(view)



                            }

                            chip_group.addView(chip_item)


                        }
                        )

                        .setNegativeButton("취소", null)

                        .create()

                        builder.setCancelable(false)



                        builder.show()









            }

            binding.addBioBtn.setOnClickListener {

                startActivity(Intent(context, AddBioActivity::class.java))


                /*val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val builder = AlertDialog.Builder(requireContext())
                val dialogView = layoutInflater.inflate(R.layout.bio_dialog, null)
                val bioText = dialogView.findViewById<EditText>(R.id.bio_input)
                val yearDate = dialogView.findViewById<NumberPicker>(R.id.year_picker)

                yearDate.wrapSelectorWheel = false

                yearDate.minValue = 2000
                yearDate.maxValue = year

                builder.setView(dialogView)
                        .setPositiveButton("등록", DialogInterface.OnClickListener { dialog, id ->


                            description = bioText.text.toString()
                            date = (yearDate.value).toString()

                            val bio = HashMap<String, Any>()
                            bio["date"] = date
                            bio["description"] = description

                                    db.collection("Users").document(userId)
                                    .collection("Bios").document(date).set(bio)





                        }
                        )

                        .setNegativeButton("취소", null)

                        .create()

                builder.setCancelable(false)



                builder.show()

                */




            }



            return fragmentProfileBinding!!.root
        }


        override fun onDestroyView() {
            fragmentProfileBinding = null
            super.onDestroyView()
        }

   private fun EventChangeListener(){



       db = FirebaseFirestore.getInstance()
       db.collection("Users").document(userId).collection("Bios").orderBy("date")
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

    override fun onClick(position: Int) {

        val builder = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.bio_update_dialog, null)
        val dialogText = dialogView.findViewById<EditText>(R.id.dialog_update)
        db = FirebaseFirestore.getInstance()

        dialogText.setText(bioArrayList[position].getDescription())


        builder.setView(dialogView)
                .setPositiveButton("업데이트", DialogInterface.OnClickListener { dialog, id ->

                    db.collection("Users").document(userId).collection("Bios")
                            .document(bioArrayList[position].getDate()).update("description", dialogText.text.toString())



                    bioArrayList.clear()
                    EventChangeListener()




                }
                )

                .setNegativeButton("취소", null)

                .create()

        builder.setCancelable(false)



        builder.show()



    }

}

