package com.example.placed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.placed.models.Institute
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.bottom_nevigation_view
import kotlinx.android.synthetic.main.activity_placed_people.*
import java.util.*
import kotlin.collections.ArrayList

class PlacedPeopleActivity : AppCompatActivity(), RecyclerAdapter.itemClicked {

    lateinit private var mAuth:FirebaseAuth
    private var layoutManager : RecyclerView.LayoutManager?=null
    private var adapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>?=null
    private var institute = ArrayList<Institute>()
    private var tempInstitute = ArrayList<Institute>()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placed_people)

        fillColleges()

        tempInstitute.addAll(institute)

        mAuth= Firebase.auth

        layoutManager = GridLayoutManager(this,1)
        recycler_view.layoutManager = layoutManager
        adapter = RecyclerAdapter(this,institute)
        recycler_view.adapter = adapter

        bottom_nevigation_view.selectedItemId = R.id.people

        bottom_nevigation_view.setOnNavigationItemSelectedListener { item->

            when(item.itemId){

                R.id.dashboard-> {
                    startActivity(Intent(this,MainActivity::class.java))
                    overridePendingTransition(0,0)
                    finish()
                }
                R.id.people-> {
                    true
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

    private fun fillColleges() {

        institute.add(Institute("NIT JAMSHEDPUR",R.drawable.nit_jamshedpur))
        institute.add(Institute("NIT TRICHY",R.drawable.ic_baseline_people_24))
        institute.add(Institute("NIT PATNA",R.drawable.nit_jamshedpur))
        institute.add(Institute("NIT RAURKELA",R.drawable.user_default_image))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

       menuInflater.inflate(R.menu.search_menu,menu)

        val item =menu!!.findItem(R.id.search_item)
        val searchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(text : String?): Boolean {
                val searchText = text!!.toLowerCase(Locale.getDefault())
                institute.clear()

                if(searchText.isNotEmpty()){

                    tempInstitute.forEach {

                        if(it.name.toLowerCase(Locale.getDefault()).contains(searchText)){

                            institute.add(it)
                        }
                    }

                    recycler_view.adapter!!.notifyDataSetChanged()
                } else {
                    institute.addAll(tempInstitute)
                    recycler_view.adapter!!.notifyDataSetChanged()
                }

                return false

            }


        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(item: String) {
        val intent = Intent(this,CompanyNameActivity::class.java)
        intent.putExtra("instituteName","$item")
        startActivity(intent)
    }

}