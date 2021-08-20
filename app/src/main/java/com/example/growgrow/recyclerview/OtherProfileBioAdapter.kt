package com.example.growgrow.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.Model.Bio
import com.example.growgrow.R
import java.util.ArrayList

class OtherProfileBioAdapter(private val bioList: ArrayList<Bio>,

) : RecyclerView.Adapter<OtherProfileBioAdapter.MyViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherProfileBioAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.bio_item, parent,false)

        return MyViewHolder(itemView)


    }

    override fun onBindViewHolder(holder: OtherProfileBioAdapter.MyViewHolder, position: Int) {

        val bio : Bio = bioList[position]
        holder.date.text = bio.getDate()
        holder.description.text = bio.getDescription()




    }

    override fun getItemCount(): Int {

        return bioList.size

    }





    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val date : TextView = itemView.findViewById(R.id.date_bio)
        val description : TextView = itemView.findViewById(R.id.description_bio)
        val editBtn : ImageButton = itemView.findViewById(R.id.edit_bio_btn)

        init {
            editBtn.visibility = View.INVISIBLE
        }










    }
}