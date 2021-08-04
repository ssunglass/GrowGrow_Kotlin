package com.example.growgrow.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.growgrow.databinding.FragmentHomeBinding
import com.example.growgrow.recyclerview.UserAdapter
import com.example.growgrow.recyclerview.UserModel
import com.google.firebase.firestore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var userArrayList : ArrayList<UserModel>
    private lateinit var myAdapter : UserAdapter
    private lateinit var db : FirebaseFirestore
    private var fragmentHomeBinding : FragmentHomeBinding? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        fragmentHomeBinding = binding

        recyclerView = binding.recyclerViewHome
        recyclerView.layoutManager = staggeredGridLayoutManager //LinearLayoutManager(requireContext())
       // recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapter = UserAdapter(userArrayList)

        recyclerView.adapter = myAdapter

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
                .addSnapshotListener(object: EventListener<QuerySnapshot>{

                    override fun onEvent(
                            value: QuerySnapshot?,
                            error: FirebaseFirestoreException?) {

                        if(error != null) {
                            Log.e("FireStore Error", error.message.toString())
                            return

                        }

                        for (dc : DocumentChange in value?.documentChanges!!){

                            if(dc.type == DocumentChange.Type.ADDED){

                                userArrayList.add(dc.document.toObject(UserModel::class.java))

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
         * @return A new instance of fragment HomeFragment.
         */

    }
}