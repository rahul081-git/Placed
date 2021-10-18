package com.example.placed

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.placed.models.User
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

class PlacedPeopleListAdapter(private val al : ArrayList<User>) : RecyclerView.Adapter<PlacedPeopleListAdapter.PeopleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.placed_people_list,parent,false)
        return PeopleViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val user = al.get(position)

        holder.ppN.text = user.displayName
        holder.ppI.text = user.instituteName
        holder.ppC.text = user.company

        Glide.with(holder.ppP.context).load(user.profileImage).circleCrop().into(holder.ppP)
    }

    override fun getItemCount(): Int {
       return al.size
    }

    class PeopleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var ppN : TextView = itemView.findViewById(R.id.placed_people_list_name)
        var ppI : TextView = itemView.findViewById(R.id.placed_people_list_institute_name)
        var ppC : TextView= itemView.findViewById(R.id.placed_people_list_company)
        var ppP : ImageView = itemView.findViewById(R.id.placed_people_list_image_view)

    }
}