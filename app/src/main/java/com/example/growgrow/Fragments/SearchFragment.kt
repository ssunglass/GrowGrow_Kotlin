package com.example.growgrow.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.Model.User
import com.example.growgrow.R
import com.example.growgrow.databinding.FragmentSearchBinding
import com.example.growgrow.recyclerview.SearchAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var myAdapter : SearchAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var recyclerView : RecyclerView
    private lateinit var query: Query
    private lateinit var options: FirestoreRecyclerOptions<User>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)


        db = FirebaseFirestore.getInstance()
        recyclerView = binding.recyclerViewSearch
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        query = db.collection("Users")
        options  = FirestoreRecyclerOptions.Builder<User>()
            .setQuery(query, User::class.java)
            .build()


        myAdapter = SearchAdapter(options)
        recyclerView.adapter = myAdapter


        binding.searchBar.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }



        })



        setupSpinnerRegion()
        setupSpinnerHandler()

        binding.expandableBtn.setOnClickListener {

            if(binding.expandableLayout.visibility == View.GONE) {
               TransitionManager.beginDelayedTransition(binding.beginLayout, AutoTransition())
                binding.expandableLayout.visibility = View.VISIBLE
                binding.expandableBtn.text = "키워드 검색"

            } else {

                TransitionManager.beginDelayedTransition(binding.beginLayout, AutoTransition())
                binding.expandableLayout.visibility = View.GONE
                binding.expandableBtn.text = "키워드 필터"


            }
            }



        // Inflate the layout for this fragment
        return binding!!.root


    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        myAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        myAdapter.startListening()

    }

    private fun setupSpinnerRegion(){
        val region = resources.getStringArray(R.array.region_array)
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            region
        )

        binding.regionFilterSpinner.adapter = adapter

    }

    private fun setupSpinnerHandler() {
        binding.regionFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        }






    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters

    }


}


