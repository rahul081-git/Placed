package com.example.placed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.placed.models.Comments

class CommentAdapter(private val al : ArrayList<Comments>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    class CommentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var userName : TextView = itemView.findViewById(R.id.commented_user_name)
        var userImage : ImageView = itemView.findViewById(R.id.commented_user_image)
        var commentText : TextView = itemView.findViewById(R.id.comment_textview)
        var userInstitute : TextView = itemView.findViewById(R.id.commented_user_institute)
        var userCompany : TextView = itemView.findViewById(R.id.commented_user_company)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item,parent,false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        val comment = al.get(position)
        holder.commentText.text= comment.text
        holder.userName.text=comment.commentedBy.displayName
        holder.userInstitute.text=comment.commentedBy.instituteName
        holder.userCompany.text=comment.commentedBy.company

        Glide.with(holder.userImage.context).load(comment.commentedBy.profileImage).circleCrop().into(holder.userImage)
    }

    override fun getItemCount(): Int {
        return al.size
    }
}