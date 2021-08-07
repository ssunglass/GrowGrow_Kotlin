package com.example.growgrow.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.Fragments.HomeFragment
import com.example.growgrow.Fragments.ProfileFragment
import com.example.growgrow.Model.User
import com.example.growgrow.R
import java.lang.reflect.Array
import java.util.ArrayList

class UserAdapter(
        private var mContext: Context,
        private val userList: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(
              R.layout.user_item, parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {

        val user : User = userList[position]
        holder.fullName.text = user.getFullname()
        holder.userName.text = user.getUsername()

        holder.itemView.setOnClickListener(View.OnClickListener {
            val pref = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("profileId", user.getUid())
            pref.apply()

            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ProfileFragment()).commit()
        })
        

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    public class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val fullName : TextView = itemView.findViewById(R.id.fullname_textview)
        val userName : TextView = itemView.findViewById(R.id.username_textview)



    }


}

// data class UserModel(var fullname:String? = null, var username:String? = null, var useruid:String? = null)

