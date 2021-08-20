package com.example.growgrow.Fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.growgrow.EditProfileActivity
import com.example.growgrow.MainActivity
import com.example.growgrow.Model.User
import com.example.growgrow.R
import com.example.growgrow.ShowProfileActivity
import com.example.growgrow.databinding.FragmentHomeBinding
import com.example.growgrow.databinding.FragmentProfileBinding
import com.example.growgrow.recyclerview.UserAdapter
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), UserAdapter.UserOnClickListener {

    private lateinit var recyclerView : RecyclerView
    private lateinit var cardview: CardView
    private lateinit var userArrayList : ArrayList<User>
    private lateinit var myAdapter : UserAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var uid: String

   // private lateinit var dialog: Dialog
    private lateinit var documentReference: DocumentReference

   private var fragmentHomeBinding : FragmentHomeBinding? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
         fragmentHomeBinding = binding


        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        documentReference = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(uid)

        cardview = binding.appBarCard
        cardview.setOnClickListener(View.OnClickListener {

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, ProfileFragment())
            transaction?.commit()

        })

        recyclerView = binding.recyclerViewHome
        recyclerView.layoutManager = staggeredGridLayoutManager //LinearLayoutManager(requireContext())
       // recyclerView.setHasFixedSize(true)
        userArrayList = arrayListOf()
        myAdapter = UserAdapter(userArrayList, this@HomeFragment)

        recyclerView.adapter = myAdapter

        if(uid.isNotEmpty()){

            documentReference
                    .addSnapshotListener( object: EventListener<DocumentSnapshot>{

                        override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {

                            binding.currentUser.text = value?.getString("fullname")
                            binding.currentUsername.text = value?.getString("username")

                        }

                    })




        }

        EventChangeListener()


        return  fragmentHomeBinding!!.root
    }


    override fun onDestroyView() {
        fragmentHomeBinding= null
        super.onDestroyView()
    }

    private fun EventChangeListener(){

        db = FirebaseFirestore.getInstance()
        db.collection("Users")
                .addSnapshotListener(object : EventListener<QuerySnapshot> {

                    override fun onEvent(
                            value: QuerySnapshot?,
                            error: FirebaseFirestoreException?) {

                        if (error != null) {
                            Log.e("FireStore Error", error.message.toString())
                            return

                        }

                        for (dc: DocumentChange in value?.documentChanges!!) {

                            if (dc.type == DocumentChange.Type.ADDED) {

                                val data = dc.document.toObject(User::class.java)

                                userArrayList.add(data)

                            }

                        }

                        myAdapter.notifyDataSetChanged()

                    }


                })


    }






   /* private fun showProgressBar(){
        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

    private fun hideProgressBar(){

        dialog.dismiss()



    }

    */




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */

    }

    override fun onClick(position: Int) {
        val intent = Intent(context, ShowProfileActivity::class.java)
        val uid = userArrayList[position].getUid()
        intent.putExtra("profileId", uid)
        startActivity(intent)
    }
}