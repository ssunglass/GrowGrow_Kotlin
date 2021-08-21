package com.example.growgrow.Fragments

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.growgrow.R
import com.example.growgrow.databinding.ActivityEditProfileBinding
import com.example.growgrow.databinding.FragmentProfileBinding
import com.example.growgrow.databinding.FragmentSearchBinding

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)


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

    private fun setupSpinnerRegion(){
        val region = resources.getStringArray(R.array.region_array)
        val adapter = ArrayAdapter(requireContext() , R.layout.support_simple_spinner_dropdown_item, region)

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