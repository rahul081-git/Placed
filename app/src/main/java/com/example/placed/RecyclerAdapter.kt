package com.example.placed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.placed.models.Institute

class RecyclerAdapter(private var listener : itemClicked,private var institute : ArrayList<Institute>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item,parent,false)
        val viewHolder = ViewHolder(v)
        v.setOnClickListener {
            listener.onItemClicked(viewHolder.institute_name.text.toString())
        }

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {

        val currInstitute = institute.get(position)

        holder.institute_name.text = currInstitute.name
        holder.recycler_view_image.setImageResource(currInstitute.image)
    }

    override fun getItemCount(): Int {
        return institute.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var recycler_view_image : ImageView
        var institute_name : TextView

        init {
            recycler_view_image = itemView.findViewById(R.id.recycler_view_image)
            institute_name = itemView.findViewById(R.id.institute_name)
        }
    }

    interface itemClicked {
        fun onItemClicked(item: String)
    }

}
