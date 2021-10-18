package com.example.placed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.placed.daos.PostDao
import com.example.placed.models.Post
import com.example.placed.models.User
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bottom_nevigation_view
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity(), IPostAdapter {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var adapter : PostAdapter
    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = Firebase.auth
        postDao = PostDao()
        
        add_post.setOnClickListener {
            startActivity(Intent(this, WritePostActivity::class.java))
        }

         setUpRecyclerView()


        bottom_nevigation_view.selectedItemId = R.id.dashboard

        bottom_nevigation_view.setOnNavigationItemSelectedListener { item->

            when(item.itemId){

                R.id.dashboard-> {
                    true
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

      return when(item.itemId) {
          R.id.logout -> {
              mAuth.signOut()
              Toast.makeText(applicationContext, "signout successful", Toast.LENGTH_SHORT).show()
              startActivity(Intent(this,IntroActivity::class.java))
              finish()
              true
          }
          R.id.delete_account -> {

                val alertDialog = AlertDialog.Builder(this)

                    alertDialog.setTitle("Delete Account")
                      .setMessage("Are you sure?")
                      .setPositiveButton("Yes"){ _, _ ->
                          deleteAccount(mAuth)

                      }
                      .setNegativeButton("No"){ _, _ ->


                      }.show()
              true
          }
          R.id.notification-> {
              startActivity(Intent(this,NotificationActivity::class.java))
              true
          }
          R.id.share_app-> {
              val i = Intent(Intent.ACTION_SEND)
              i.type = "text/plain"
              intent.putExtra(Intent.EXTRA_TEXT,"Hey checkout this App ")
              val chooser=Intent.createChooser(intent,"Share using...")
              startActivity(chooser)
              true
          }
          else ->{
              false
          }

      }
        return super.onOptionsItemSelected(item)
    }

    fun deleteAccount(user : FirebaseAuth){

        user.currentUser!!.delete()
                .addOnCompleteListener{task->
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext,"Account deleted successfully",Toast.LENGTH_SHORT).show()
                        finish()
                        startActivity(Intent(applicationContext,IntroActivity::class.java))
                    }

                }

    }
    private fun setUpRecyclerView() {

        val db = FirebaseFirestore.getInstance()
        val postCollection = db.collection("post")

        val query = postCollection.orderBy("createdAt",Query.Direction.DESCENDING)

        val recyclerViewOption = FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()

        adapter = PostAdapter(recyclerViewOption , this)

        post_recycler_view.adapter = adapter
        post_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
    override fun onLikeClicked(postId : String){
         PostDao().updateLikes(postId)
    }

    override fun onCommentClicked(postId: String) {
        val intent = Intent(this,PostComment::class.java)
        intent.putExtra("postId",postId.toString())
        startActivity(intent)
    }
}