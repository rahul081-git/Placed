package com.example.placed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.placed.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class PostAdapter(options: FirestoreRecyclerOptions<Post>?, private var listener : IPostAdapter) : FirestoreRecyclerAdapter<Post , PostAdapter.PostViewHolder>(
    options as FirestoreRecyclerOptions<Post>
) {

    class PostViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val userImage : ImageView = itemView.findViewById(R.id.user_profile_image_post)
        val userName : TextView = itemView.findViewById(R.id.post_usernme)
        val createdAt : TextView = itemView.findViewById(R.id.created_at_post)
        val likeImage : ImageView = itemView.findViewById(R.id.post_like)
        val likeCount : TextView = itemView.findViewById(R.id.like_count_post)
        val postText : TextView = itemView.findViewById(R.id.user_post_input)
        val postComment : ImageView = itemView.findViewById(R.id.post_comment);
        val commentCount : TextView = itemView.findViewById(R.id.comment_count_post)
        var userInstitute : TextView = itemView.findViewById(R.id.post_user_institute)
        var userCompany : TextView = itemView.findViewById(R.id.post_user_company)
        val postImage:ImageView = itemView.findViewById(R.id.post_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.post_item_recycler_view,parent,false)
        val viewHolder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_item_recycler_view,parent,false))

        viewHolder.likeImage.setOnClickListener{
            listener.onLikeClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        viewHolder.postComment.setOnClickListener {
            listener.onCommentClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }
    override fun onBindViewHolder(holder : PostViewHolder, position : Int, model : Post) {
            holder.postText.text = model.text
            holder.userName.text = model.createdBy.displayName.toString()
            holder.likeCount.text = model.likedBy.size.toString()
            holder.createdAt.text = Utils.getTimeAgo(model.createdAt)
            holder.userInstitute.text = model.createdBy.instituteName
            holder.userCompany.text = model.createdBy.company

        Glide.with(holder.postImage.context).load(model.image).into(holder.postImage)

        Glide.with(holder.userImage.context).load(model.createdBy.profileImage).circleCrop().into(holder.userImage)

        val currentUserId = Firebase.auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUserId)


        if(isLiked) {

            holder.likeImage.setImageDrawable(ContextCompat.getDrawable(holder.likeImage.context,R.drawable.ic_favorite_filled_color))
        } else {

            holder.likeImage.setImageDrawable(ContextCompat.getDrawable(holder.likeImage.context,R.drawable.ic_favorite_border))
        }
    }
}
interface IPostAdapter{
    fun onLikeClicked(postId : String)

    fun onCommentClicked(postId : String)
}