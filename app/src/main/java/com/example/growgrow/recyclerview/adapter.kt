package com.example.growgrow.recyclerview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.Fragments.HomeFragment
import com.example.growgrow.Fragments.ProfileFragment
import com.example.growgrow.Model.User
import com.example.growgrow.R
import java.lang.reflect.Array
import java.util.*

class UserAdapter(
        private val userList: ArrayList<User>,
        val listener: UserOnClickListener,
        private var mContext: Context
        ) : RecyclerView.Adapter<UserAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(
              R.layout.user_item, parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {


        val random = Random()
        val colors:List<Int> = mutableListOf(R.color.user_color1,R.color.user_color2,R.color.user_color3,R.color.user_color4)

        val drawable = ContextCompat.getDrawable(mContext,R.drawable.user_layout_shape) as GradientDrawable?

        drawable?.setColor(ContextCompat.getColor(mContext,colors[random.nextInt(colors.size)]))

        val user : User = userList[position]
        holder.fullName.text = user.getFullname()
        holder.userName.text = "@${user.getUsername()}"
        holder.depart.text = user.getDepart()
        holder.major.text = user.getMajor()
        holder.summary.text = user.getSummary()





      /*  holder.itemView.setOnClickListener(View.OnClickListener {
            val pref = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("profileId", user.getUid())
            pref.apply()

            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ProfileFragment()).commit()
        })

       */
        

    }

    interface UserOnClickListener{
        fun onClick(position: Int)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val fullName : TextView = itemView.findViewById(R.id.fullname_textview)
        val userName : TextView = itemView.findViewById(R.id.username_textview)
        val depart : TextView = itemView.findViewById(R.id.depart_textview)
        val major : TextView = itemView.findViewById(R.id.major_textview)
        val summary : TextView = itemView.findViewById(R.id.summary_testview)



        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                listener.onClick(position)
            }
        }


    }


}

// data class UserModel(var fullname:String? = null, var username:String? = null, var useruid:String? = null)

