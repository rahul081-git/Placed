package com.example.placed.daos

import com.example.placed.models.PlacedUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class PlacedUserDao {
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = Firebase.auth

    val proRef = db.collection("users").document(mAuth.currentUser!!.uid)

    fun addPlacedUser(placedUser : PlacedUser){
        placedUser.let {
            proRef.set(it, SetOptions.merge())
        }
    }
}
