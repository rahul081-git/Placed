package com.example.placed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.placed.models.Company
import kotlinx.android.synthetic.main.activity_company_name.*
import kotlinx.android.synthetic.main.activity_placed_people.*
import java.util.*
import kotlin.collections.ArrayList

class CompanyNameActivity : AppCompatActivity() , CompanyRecyclerAdapter.comapnyItemClicked{

    private var layoutManager : RecyclerView.LayoutManager?=null
    private var adapter : RecyclerView.Adapter<CompanyRecyclerAdapter.ViewHolder>?=null
    private var detail = String()
    private var company = ArrayList<Company>()
    private var tempCompany = ArrayList<Company>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_name)

        fillCompany()

        tempCompany.addAll(company)

        layoutManager = GridLayoutManager(this,2)
        company_name_recycler_view.layoutManager = layoutManager

        adapter = CompanyRecyclerAdapter(this,company)
        company_name_recycler_view.adapter = adapter

        detail = (intent.getStringExtra("instituteName").toString())
    }

    private fun fillCompany() {
        company.add(Company("AMAZON",R.drawable.amazon))
        company.add(Company("MICROSOFT",R.drawable.microsoft))
        company.add(Company("OLA",R.drawable.ola))
        company.add(Company("OYO",R.drawable.oyo))
        company.add(Company("FLIPKART",R.drawable.flipkart))
        company.add(Company("GOOGLE",R.drawable.google))
        company.add(Company("ADOBE",R.drawable.adobe))
        company.add(Company("ZOHO",R.drawable.zoho))
        company.add(Company("SAMSUNG",R.drawable.samsung))
        company.add(Company("WALMART",R.drawable.walmart))
        company.add(Company("FACTSET",R.drawable.factset))
        company.add(Company("QUALCOMN",R.drawable.qualcomm))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_menu,menu)


        val item = menu!!.findItem(R.id.search_item)
        val searchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(text: String?): Boolean {
                        val searchText = text!!.toLowerCase(Locale.getDefault())
                        company.clear()

                        if(searchText.isNotEmpty()){

                            tempCompany.forEach {

                                if(it.name.toLowerCase(Locale.getDefault()).contains(searchText)){

                                    company.add(it)
                                }
                            }

                            company_name_recycler_view.adapter!!.notifyDataSetChanged()
                        } else {
                            company.addAll(tempCompany)
                            company_name_recycler_view.adapter!!.notifyDataSetChanged()
                        }

                        return false
                    }

                }
        )
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return super.onOptionsItemSelected(item)
    }

    override fun onCompanyItemClicked(item: String) {

        var info = ArrayList<String>()
        info.add(detail)
        info.add(item)
        val intent = Intent(this,ListOfPlacedPeople::class.java)
        intent.putStringArrayListExtra("details",info)
        startActivity(intent)
    }
}
