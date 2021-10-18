package com.example.placed

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.placed.models.InterViewExperience
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class InterviewExperienceAdapter(options: FirestoreRecyclerOptions<InterViewExperience>) : FirestoreRecyclerAdapter<InterViewExperience , InterviewExperienceAdapter.ViewHolder>(
        options
) {



    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
       val userName : TextView = itemView.findViewById(R.id.interview_usernme)
        val userCompany : TextView = itemView.findViewById(R.id.interview_user_company)
        val userInstitute : TextView = itemView.findViewById(R.id.interview_user_institute)
        val InterviewText : TextView = itemView.findViewById(R.id.interview_exp_text)
        val userImage : ImageView = itemView.findViewById(R.id.interview_user_profile_image_post)
        val InterviewedCompany : TextView = itemView.findViewById(R.id.company_name_interview_list)
        val writtenTime : TextView = itemView.findViewById(R.id.created_at_interview_experience)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.interview_item,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: InterViewExperience) {
        holder.userName.text = model.writtenBy.displayName.toString()
        holder.userCompany.text = model.writtenBy.company.toString()
        holder.userInstitute.text = model.writtenBy.instituteName.toString()
        holder.InterviewText.text = model.text
        holder.InterviewedCompany.text = model.company
        holder.writtenTime.text = Utils.getTimeAgo(model.writtenTime)

        Glide.with(holder.userImage.context).load(model.writtenBy.profileImage).circleCrop().into(holder.userImage)
    }
}