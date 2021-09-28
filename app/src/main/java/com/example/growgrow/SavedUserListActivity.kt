package com.example.growgrow

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.Fragments.LinearLayoutManagerWrapper
import com.example.growgrow.Model.Bio
import com.example.growgrow.Model.User
import com.example.growgrow.databinding.ActivitySavedUserListBinding
import com.example.growgrow.recyclerview.SearchAdapter
import com.example.growgrow.recyclerview.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class SavedUserListActivity : AppCompatActivity(), UserAdapter.UserOnClickListener  {

    private var _binding: ActivitySavedUserListBinding? = null
    private val binding get() = _binding!!
    private lateinit var db : FirebaseFirestore
    private lateinit var savedUserArrayList : ArrayList<User>
    private lateinit var recyclerView : RecyclerView
    private lateinit var myAdapter: UserAdapter
    private lateinit var auth : FirebaseAuth
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySavedUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid.toString()

        val linearLayoutManagerWrapepr = LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL, false)



        recyclerView = binding.savedUserRecyclerview
        recyclerView.layoutManager = linearLayoutManagerWrapepr
        savedUserArrayList = arrayListOf()

        myAdapter = UserAdapter(savedUserArrayList, this@SavedUserListActivity, this)
        recyclerView.adapter = myAdapter


        EventChangeListener()


    }

    private fun EventChangeListener(){

        db = FirebaseFirestore.getInstance()
        db.collection("Users")
            .document(userId)
            .collection("SavedUsers")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {

                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?) {

                    savedUserArrayList.clear()

                    if (error != null) {
                        Log.e("FireStore Error", error.message.toString())
                        return

                    }

                    for(d:DocumentSnapshot in value?.documents!!){

                        val data = d.toObject(User::class.java)


                        if (data != null) {
                            savedUserArrayList.add(data)

                        }


                    }

                    myAdapter.notifyDataSetChanged()



                }


            })


    }

    override fun onClick(position: Int) {

        val intent = Intent(this, ShowProfileActivity::class.java)
        val uid = savedUserArrayList[position].getUid()
        intent.putExtra("profileId", uid)
        startActivity(intent)

    }
}

class LinearLayoutManagerWrapper: LinearLayoutManager {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}
    override fun supportsPredictiveItemAnimations(): Boolean { return false }
}