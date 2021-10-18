package com.example.placed.daos

import com.example.placed.models.Comments
import com.example.placed.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CommentDao {

    val db = FirebaseFirestore.getInstance()
    val commentCollection=db.collection("comments")
    val currentUserId = Firebase.auth.currentUser!!.uid

    fun addComment(text : String, postId : String){
        GlobalScope.launch {

            val user = UserDao().getUserById(currentUserId).await().toObject(User::class.java)!!

            val comment = Comments(text,user)

            commentCollection.document(postId).collection("Publisher").add(comment)
        }
    }

}