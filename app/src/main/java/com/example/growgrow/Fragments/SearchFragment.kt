package com.example.growgrow.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.Log
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
import com.example.growgrow.ShowProfileActivity
import com.example.growgrow.databinding.FragmentSearchBinding
import com.example.growgrow.recyclerview.SearchAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment(), SearchAdapter.OnItemClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var myAdapter: SearchAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var query: Query
    private lateinit var options: FirestoreRecyclerOptions<User>
    private lateinit var filterRegion: String
    private lateinit var selectedChips: ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)


        db = FirebaseFirestore.getInstance()
        //  recyclerView = binding.recyclerViewSearch
        //  val linearLayoutManagerWrapepr = LinearLayoutManagerWrapper(requireContext(), LinearLayoutManager.VERTICAL, false)

        selectedChips = arrayListOf()
        //   filterRegion = ""

        //   recyclerView.layoutManager = linearLayoutManagerWrapepr //LinearLayoutManager(requireContext())

        query = db.collection("Users")
        options = FirestoreRecyclerOptions.Builder<User>()
            .setQuery(query, User::class.java)
            .build()


        //  myAdapter = SearchAdapter(options  ,this@SearchFragment)
        //   recyclerView.adapter = myAdapter


        binding.initConditionChip.setOnClickListener {

            val search = binding.searchBar.text.toString()

            selectedChips.clear()

            binding.searchDepartChipgroup.checkedChipIds.forEach {
                val chip = binding.root.findViewById<Chip>(it).text.toString()
                selectedChips.add(chip)
            }

            //  this.filterRegion = binding.filteredRegion.text.toString()


            /*  query = if(filterRegion.isNotEmpty()){

                db.collection("Users").whereEqualTo("region", filterRegion)


            } else {

                db.collection("Users")

            }


           */

            when {
                search.isEmpty() && filterRegion.isEmpty() && selectedChips.isEmpty() -> query =
                    db.collection("Users")
                search.isEmpty() && filterRegion.isEmpty() && selectedChips.isNotEmpty() -> query =
                    db.collection("Users").whereIn("depart", selectedChips)
                search.isEmpty() && filterRegion.isNotEmpty() && selectedChips.isEmpty() -> query =
                    db.collection("Users").whereEqualTo("region", filterRegion)
                search.isEmpty() && filterRegion.isNotEmpty() && selectedChips.isNotEmpty() -> query =
                    db.collection("Users").whereIn("depart", selectedChips)
                        .whereEqualTo("region", filterRegion)


                search.isNotEmpty() && filterRegion.isEmpty() && selectedChips.isEmpty() -> query =
                    db.collection("Users").whereArrayContains("keywords_search", search)
                search.isNotEmpty() && filterRegion.isEmpty() && selectedChips.isNotEmpty() -> query =
                    db.collection("Users").whereArrayContains("keywords_search", search)
                        .whereIn("depart", selectedChips)
                search.isNotEmpty() && filterRegion.isNotEmpty() && selectedChips.isEmpty() -> query =
                    db.collection("Users").whereArrayContains("keywords_search", search)
                        .whereEqualTo("region", filterRegion)


                else -> {

                    query = db.collection("Users")
                        .whereArrayContains("keywords_search", search)
                        .whereEqualTo("region", filterRegion)
                        .whereIn("depart", selectedChips)


                }


            }














            options = FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User::class.java)
                .build()

            //  TransitionManager.beginDelayedTransition(binding.beginLayout, AutoTransition())
            //  binding.expandableLayout.visibility = View.GONE
            //  binding.expandableBtn.text = "키워드 필터"

            myAdapter.updateOptions(options)
            myAdapter.notifyDataSetChanged()


        }

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                //  filterRegion = binding.filteredRegion.text.toString()
                val searchText = s.toString()

                when {
                    searchText.isEmpty() && filterRegion.isEmpty() && selectedChips.isEmpty() -> query =
                        db.collection("Users")
                    searchText.isEmpty() && filterRegion.isEmpty() && selectedChips.isNotEmpty() -> query =
                        db.collection("Users").whereIn("depart", selectedChips)
                    searchText.isEmpty() && filterRegion.isNotEmpty() && selectedChips.isEmpty() -> query =
                        db.collection("Users").whereEqualTo("region", filterRegion)
                    searchText.isEmpty() && filterRegion.isNotEmpty() && selectedChips.isNotEmpty() -> query =
                        db.collection("Users").whereIn("depart", selectedChips)
                            .whereEqualTo("region", filterRegion)


                    searchText.isNotEmpty() && filterRegion.isEmpty() && selectedChips.isEmpty() -> query =
                        db.collection("Users").whereArrayContains("keywords_search", searchText)
                    searchText.isNotEmpty() && filterRegion.isEmpty() && selectedChips.isNotEmpty() -> query =
                        db.collection("Users").whereArrayContains("keywords_search", searchText)
                            .whereIn("depart", selectedChips)
                    searchText.isNotEmpty() && filterRegion.isNotEmpty() && selectedChips.isEmpty() -> query =
                        db.collection("Users").whereArrayContains("keywords_search", searchText)
                            .whereEqualTo("region", filterRegion)


                    else -> {

                        query = db.collection("Users")
                            .whereArrayContains("keywords_search", searchText)
                            .whereEqualTo("region", filterRegion)
                            .whereIn("depart", selectedChips)


                    }


                }


                /* if(s.toString().isEmpty()) {

                    query = db.collection("Users").whereEqualTo("region", filterRegion)


                } else {
                    query = db.collection("Users")
                        .whereArrayContains("keywords", s.toString()).whereEqualTo("region", filterRegion)
                        

                }

                */




                options = FirestoreRecyclerOptions.Builder<User>()
                    .setQuery(query, User::class.java)
                    .build()



                myAdapter.updateOptions(options)
                myAdapter.notifyDataSetChanged()


            }


        })


        // setupSpinnerRegion()
        // setupSpinnerHandler()

        /* binding.expandableBtn.setOnClickListener {

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

        */


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
        myAdapter.stopListening()

    }



  /*  private fun setupSpinnerRegion(){
        val region = resources.getStringArray(R.array.region_array)
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            region
        )

        binding.regionFilterSpinner.adapter = adapter

    }

   */

    /* private fun setupSpinnerHandler() {
        binding.regionFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                val currentItem = binding.regionFilterSpinner.getItemAtPosition(position)

                if(!currentItem.equals("지역")) {

                    binding.filteredRegion.text = currentItem.toString()


                } else {
                    binding.filteredRegion.text = ""


                }



            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        } */






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

    override fun onItemClick(position: Int) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        val intent = Intent(context, ShowProfileActivity::class.java)
        val uid = myAdapter.getItem(position).getUid()
        intent.putExtra("profileId", uid)
        startActivity(intent)

    }

    /*  override fun onItemClick(position: Int) {
          val intent = Intent(context, ShowProfileActivity::class.java)
          val uid = myAdapter.getItem(position).getUid()
          intent.putExtra("profileId", uid)
          startActivity(intent)
      }


     */

}

class LinearLayoutManagerWrapper: LinearLayoutManager {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}
    override fun supportsPredictiveItemAnimations(): Boolean { return false }
}






