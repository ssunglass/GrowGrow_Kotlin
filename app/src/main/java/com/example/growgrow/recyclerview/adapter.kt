package com.example.growgrow.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.Fragments.HomeFragment
import com.example.growgrow.R
import java.lang.reflect.Array
import java.util.ArrayList

class UserAdapter(private val userList: ArrayList<UserModel>) : RecyclerView.Adapter<UserAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(
              R.layout.user_item, parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {

        val user : UserModel = userList[position]
        holder.fullName.text = user.fullname
        holder.userName.text = user.username

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    public class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val fullName : TextView = itemView.findViewById(R.id.fullname_textview)
        val userName : TextView = itemView.findViewById(R.id.username_textview)



    }


}

data class UserModel(var fullname:String? = null, var username:String? = null)

