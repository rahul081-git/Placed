package com.example.placed.daos

import android.widget.Toast
import com.example.placed.Utils
import com.example.placed.models.InterViewExperience
import com.example.placed.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class InterViewExperienceDao {

    fun addInterViewExperience( text : String , company:String, writtenBy : String){

        GlobalScope.launch {
            val user = UserDao().getUserById(writtenBy).await().toObject(User::class.java)
            val currentTime = System.currentTimeMillis()


            val exp = user?.let {
//                if (currentTime != null) {
                    InterViewExperience(text,company, it,currentTime)

            }

            if (exp != null) {
                FirebaseFirestore.getInstance()
                        .collection("InterviewExperience")
                        .document()
                        .set(exp)
            }

        }



    }
}