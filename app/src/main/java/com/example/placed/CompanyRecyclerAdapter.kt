package com.example.placed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.placed.models.Company

class CompanyRecyclerAdapter(private var listener : comapnyItemClicked,private var company : ArrayList<Company>) : RecyclerView.Adapter<CompanyRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.company_name_recycler_view_item,parent,false)

        val viewHolder = ViewHolder(v)

        v.setOnClickListener {
            listener.onCompanyItemClicked(viewHolder.company_name.text.toString())
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: CompanyRecyclerAdapter.ViewHolder, position: Int) {

        val currCompany = company.get(position)
        holder.company_name.text = currCompany.name
        holder.companyLogo.setImageResource(currCompany.image)
    }

    override fun getItemCount(): Int {
      return  company.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       var company_name : TextView
       var companyLogo : ImageView

       init {
           company_name = itemView.findViewById(R.id.company_name)
           companyLogo = itemView.findViewById(R.id.company_logo)
       }
    }
    interface comapnyItemClicked{
        fun onCompanyItemClicked(item : String)
    }
}

