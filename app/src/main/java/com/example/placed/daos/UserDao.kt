package com.example.placed.daos

import com.example.placed.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class UserDao {

    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")

    fun adduser(user : User){
        user?.let {
            userCollection.document(user.uid).set(it)
        }
    }

    fun getUserById(uId : String) : Task<DocumentSnapshot> {
        return userCollection.document(uId).get()
    }
}