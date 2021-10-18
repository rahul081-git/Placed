package com.example.placed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.placed.models.User
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_list_of_placed_people.*

class ListOfPlacedPeople : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var adapter : PlacedPeopleListAdapter
    private var details = ArrayList<String>()
    private val users = ArrayList<User>()
    private val tempUsers = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_placed_people)
        details = intent.getStringArrayListExtra("details") as ArrayList<String>
        setUpRecyclerView()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecyclerView() {
        adapter = PlacedPeopleListAdapter(users)
        placed_people_list_recycler_view.adapter= adapter
        placed_people_list_recycler_view.layoutManager=GridLayoutManager(this,2)

        FirebaseFirestore.getInstance()
                .collection("users")
                .whereEqualTo("instituteName","${details!!.get(0)}")
                .whereEqualTo("company","${details.get(1)}")
                .get().addOnSuccessListener {
            for (doc in it){
                val user=doc.toObject(User::class.java)
                users.add(user)
            }
            adapter.notifyDataSetChanged()
        }

        Toast.makeText(this,"${details!!.get(1)}",Toast.LENGTH_SHORT).show()
    }
}


