package com.example.placed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.placed.daos.InterViewExperienceDao
import com.example.placed.models.InterViewExperience
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_write_interview_experience.*

class WriteInterviewExperienceActivity : AppCompatActivity() {

    private var companyName = arrayOf("AMAZON","MICROSOFT","OLA","OYO","FLIPKART","GOOGLE","ADOBE","ZOHO","SAMSUNG","WALMART","FACTSET"
    ,"QUALCOMN")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_interview_experience)

        // Autocomplete textview for company

        val adapter : ArrayAdapter<String> = ArrayAdapter(
            this,android.R.layout.simple_list_item_1,companyName
        )
        interview_experience_company.setAdapter(adapter)


        // posting an Interview Experience
        post_interview_experience_btn.setOnClickListener {

           if(validateInfo(interview_experience_text.text.toString(), interview_experience_company.text.toString())){
               InterViewExperienceDao().addInterViewExperience(
                   interview_experience_text.text.toString(),
                   interview_experience_company.text.toString(),
                   Firebase.auth.currentUser!!.uid
               )
           }
        }
    }

    private fun validateInfo(text : String, company : String) : Boolean{

        var massage=""

        if(company.equals("")){
            massage = "company"
        } else if(text.equals(""))
            massage = "text"

        if(!massage.equals("")){
            Toast.makeText(this,"$massage cannot be empty",Toast.LENGTH_SHORT).show()
            return false
        }
        return true

    }
}