package com.example.placed.daos

import android.widget.Toast
import com.example.placed.PostAdapter
import com.example.placed.models.Post
import com.example.placed.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {

    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("post")
    val mAuth = Firebase.auth

    fun addPost(text : String , image : String){
        val currentUserId = mAuth.currentUser!!.uid

        GlobalScope.launch {

            val user = UserDao().getUserById(currentUserId).await().toObject(User::class.java)!!

            val currentTime = System.currentTimeMillis()
            val post = Post(text,image,user,currentTime)

            postCollection.document().set(post)
        }
    }
    fun getPostById(postId: String) : Task<DocumentSnapshot> {
        return postCollection.document(postId).get()
    }

    fun updateLikes(postId : String){
        GlobalScope.launch {
            val currentUserId = mAuth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)
            val isLiked = post?.likedBy?.contains(currentUserId)

            if(isLiked == true){
                post?.likedBy?.remove(currentUserId)
            } else {
                post?.likedBy?.add(currentUserId)
            }
            post?.let { postCollection.document(postId).set(it) }

        }
    }
}