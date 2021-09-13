package com.example.growgrow.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.EditProfileActivity
import com.example.growgrow.R

class MajorAdapter(
        private val majorList: ArrayList<String>,
        val listener: EditProfileActivity
):RecyclerView.Adapter<MajorAdapter.MyViewHolder>(),Filterable {

    var majorFilterList = ArrayList<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MajorAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.major_item, parent,false)


        return MyViewHolder(itemView)

    }

    init{
         majorFilterList = majorList as ArrayList<String>


    }

    override fun onBindViewHolder(holder: MajorAdapter.MyViewHolder, position: Int) {


        val majorText: String = majorFilterList[position]

       holder.majorName.text = majorText

    }

    interface UserOnClickListener{
        fun onClick(position: Int)
    }

    override fun getItemCount(): Int {

        return majorFilterList.size

    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val majorName = itemView.findViewById<TextView>(R.id.major_name)

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                listener.onClick(position)
            }
        }




    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    majorFilterList =  majorList as ArrayList<String>
                } else {
                    val resultList = ArrayList<String>()
                    for (row in majorList) {
                        if (row.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    majorFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = majorFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                majorFilterList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }
        }

    }


}