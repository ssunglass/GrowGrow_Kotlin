package com.example.growgrow.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.growgrow.Model.User
import com.example.growgrow.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class SearchAdapter : FirestoreRecyclerAdapter<User, SearchAdapter.SearchViewHolder> {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SearchViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_item, parent, false)
        return SearchViewHolder(v)
    }


       interface OnItemClickListener {
           fun onItemClick(position: Int)
       }


       private val listener: OnItemClickListener?


      internal constructor(options: FirestoreRecyclerOptions<User?>?, listener: OnItemClickListener?) : super(options!!) {
           this.listener = listener
       }

       internal constructor(options: FirestoreRecyclerOptions<User>) : super(options!!) {
           listener = null
       }




    // Replace the contents of a view (invoked by the layout manager)

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int, model: User) {
       holder.fullName.text = model.getFullname()
        holder.userName.text = model.getUsername()
        holder.depart.text = model.getDepart()
        holder.major.text = model.getMajor()
        holder.summary.text = model.getSummary()

        if (listener != null) {
            holder.itemView.setOnClickListener(View.OnClickListener {
                listener.onItemClick(holder.bindingAdapterPosition)
            })

        }
    }


    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val fullName: TextView
        val userName: TextView
        val depart: TextView
        val major: TextView
        val summary: TextView

        init {
            fullName = itemView.findViewById(R.id.fullname_textview)
            userName = itemView.findViewById(R.id.username_textview)
            major = itemView.findViewById(R.id.major_textview)
            depart = itemView.findViewById(R.id.depart_textview)
            summary = itemView.findViewById(R.id.summary_testview)
        }
    }



    // Create new views (invoked by the layout manager)

}
