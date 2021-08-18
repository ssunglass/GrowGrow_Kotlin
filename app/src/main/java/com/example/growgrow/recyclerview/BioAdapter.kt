package com.example.growgrow.recyclerview

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.Model.Bio
import com.example.growgrow.Model.User
import com.example.growgrow.R
import java.util.ArrayList

 class BioAdapter(
        private val bioList: ArrayList<Bio>,
        val listener: MyOnClickListener

) : RecyclerView.Adapter<BioAdapter.MyViewHolder>(){

    //  private var listener: View.OnClickListener? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BioAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.bio_item, parent,false)

        return MyViewHolder(itemView)



    }

    override fun onBindViewHolder(holder: BioAdapter.MyViewHolder, position: Int) {

        val bio : Bio = bioList[position]
        holder.date.text = bio.getDate()
        holder.description.text = bio.getDescription()






    }

    override fun getItemCount(): Int {

        return bioList.size

    }

     interface MyOnClickListener{
         fun onClick(position: Int)
     }

     fun deleteItem(i: Int){

         bioList.removeAt(i)
         notifyDataSetChanged()



     }

    /* fun setOnClickListener(listener: View.OnClickListener) {
         this.listener = listener
     }


     */





     inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val date : TextView = itemView.findViewById(R.id.date_bio)
        val description : TextView = itemView.findViewById(R.id.description_bio)
        val editBtn : ImageButton = itemView.findViewById(R.id.edit_bio_btn)


         init {
             editBtn.setOnClickListener {
                 val position = adapterPosition
                 listener.onClick(position)
             }
         }







    }
}

