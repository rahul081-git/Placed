package com.example.placed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_create_or_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bottom_nevigation_view

class CreateOrAddActivity : AppCompatActivity() {

    lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_or_add)

        mAuth = Firebase.auth

        add_placed_user.setOnClickListener {
            startActivity(Intent(this,RegisterPlacedUser::class.java))
        }
        next.setOnClickListener {
            startActivity(Intent(this,ListOfPlacedPeople::class.java))
        }
        comment_btn.setOnClickListener {
            startActivity(Intent(this,DemoActivity::class.java))
        }

        bottom_nevigation_view.selectedItemId = R.id.create

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
                    true
                }
                R.id.questions-> {
                    startActivity(Intent(this,InterViewQuestionsActivity::class.java))
                    overridePendingTransition(0,0)
                    finish()
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

}