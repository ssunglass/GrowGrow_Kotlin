package com.example.growgrow.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.Model.Bio
import com.example.growgrow.R

class MajorAdapter(
        private val majorList: ArrayList<String>
):RecyclerView.Adapter<MajorAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MajorAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.major_item, parent,false)


        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MajorAdapter.MyViewHolder, position: Int) {


        val majorText: String = majorList[position]

       holder.majorName.text = majorText

    }

    override fun getItemCount(): Int {

        return majorList.size

    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val majorName = itemView.findViewById<TextView>(R.id.major_name)




    }
}