package com.example.placed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.placed.daos.InterViewExperienceDao
import com.example.placed.daos.PostDao
import com.example.placed.models.InterViewExperience
import com.example.placed.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_inter_view_questions.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bottom_nevigation_view

class InterViewQuestionsActivity : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    private lateinit var adapter : InterviewExperienceAdapter
    private lateinit var interviewDao: InterViewExperienceDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inter_view_questions)

        mAuth = Firebase.auth
        interviewDao= InterViewExperienceDao()

        setRecyclerView()

        add_interview_experience.setOnClickListener {
            startActivity(Intent(this,WriteInterviewExperienceActivity::class.java))
        }

        bottom_nevigation_view.selectedItemId = R.id.questions

        bottom_nevigation_view.setOnNavigationItemSelectedListener { item->

            when(item.itemId){

                R.id.dashboard-> {
                    startActivity(Intent(this,MainActivity::class.java))
                    overridePendingTransition(0,0)
                    finish()
                }
                R.id.people-> {
                    startActivity(Intent(this,PlacedPeopleActivity::class.java))
                    overridePendingTransition(0,0)
                    finish()
                }
                R.id.create-> {
                    startActivity(Intent(this,CreateOrAddActivity::class.java))
                    overridePendingTransition(0,0)
                    finish()
                }
                R.id.questions-> {
                    true
                }
                R.id.profile-> {

                    startActivity(Intent(this,UserProfileActivity::class.java))
                    overridePendingTransition(0,0)
                    finish()
                }
            }
            true
        }
    }

    private fun setRecyclerView() {


        val db = FirebaseFirestore.getInstance()
        val postCollection = db.collection("InterviewExperience")

        val query = postCollection.orderBy("company", Query.Direction.DESCENDING)

        val recyclerViewOption = FirestoreRecyclerOptions.Builder<InterViewExperience>().setQuery(query, InterViewExperience::class.java).build()

        adapter = InterviewExperienceAdapter(recyclerViewOption)

        interview_recycler_view.adapter = adapter
        interview_recycler_view.layoutManager = LinearLayoutManager(this)
    }


    override fun onStart() {
        super.onStart()

        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()

        adapter.stopListening()
    }
}