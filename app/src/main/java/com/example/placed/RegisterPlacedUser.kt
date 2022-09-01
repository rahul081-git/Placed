package com.example.placed

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.placed.daos.PlacedUserDao
import com.example.placed.models.PlacedUser
import com.example.placed.models.User
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register_placed_user.*
import kotlinx.android.synthetic.main.activity_write_interview_experience.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterPlacedUser : AppCompatActivity() {

    lateinit var filePath : Uri
    private var isRegistered = false

    private var instituteName = arrayOf("Indian Institute of Technology, Bombay", "Indian Institute of Technology, Delhi",
                            "Indian Institute of Technology, Kanpur","Indian Institute of Technology, Patna",
                            "National Institute of Technology, Jamshedpur","National Institute of Technology, Trichy",
                            "National Institute of Technology, Raurkela","National Institute of Technology, Patna",)

    private var companyName = arrayOf("AMAZON","MICROSOFT","OLA","OYO","FLIPKART","GOOGLE","ADOBE","ZOHO","SAMSUNG","WALMART","FACTSET"
            ,"QUALCOMN")

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_placed_user)

        val adapter : ArrayAdapter<String> = ArrayAdapter(
                this,android.R.layout.simple_list_item_1,instituteName
        )
        placed_user_institute_name.setAdapter(adapter)

            val adapter1 : ArrayAdapter<String> = ArrayAdapter(
                    this,android.R.layout.simple_list_item_1,companyName
            )
            placed_user_company.setAdapter(adapter1)

            val userSnapShot = FirebaseFirestore.getInstance()
                .collection("users")
                .document(Firebase.auth.currentUser!!.uid)
                .get()
        GlobalScope.launch {
            val user = userSnapShot.await().toObject(User::class.java)

            if (user != null) {
                Log.i("isRegistered",user.isRegistered.toString())
            }
        }


        if(isRegistered)
            register_placed_user.isEnabled = false

        register_placed_user.setOnClickListener {it->
            PlacedUserDao().addPlacedUser(
                    PlacedUser(
                            placed_user_institute_name.text.toString(),
                            placed_user_branch.text.toString(),
                            placed_user_batch.text.toString(),
                            placed_user_company.text.toString(),
                            true
                    )
            )
            Toast.makeText(this,"Registered",Toast.LENGTH_SHORT).show()
        }

        upload_resume_btn.setOnClickListener {
            startFileChooser()
        }
    }
    public fun startFileChooser() {
        ImagePicker.with(this)
                .crop()
                .compress(1080)
                .maxResultSize(1080,1080)
                .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== RESULT_OK && data!=null){
            filePath= data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
            resume_image_view.setImageBitmap(bitmap)
        }
    }
}