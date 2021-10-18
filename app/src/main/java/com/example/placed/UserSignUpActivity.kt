package com.example.placed

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.placed.daos.UserDao
import com.example.placed.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_user_sign_up.*

class UserSignUpActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var filePath : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign_up)

        mAuth = Firebase.auth

        signup_button.setOnClickListener {

                if (validateForm(signup_name_edittext.text.toString().trim(), signup_username_edittext.text.toString().trim(), signup_password_edittext.text.toString().trim()))

                   signUp(signup_name_edittext.text.toString().trim(), signup_username_edittext.text.toString().trim(), signup_password_edittext.text.toString().trim())

           }

        signin_text.setOnClickListener {
            finish()
        }

        }
    fun signUp(name: String, email:String, password: String){

        var progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Registering")
        progressDialog.show()

        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {task->

                if(task.isSuccessful){

                    val user = User(mAuth.currentUser?.uid.toString(),name,email)
                    val usersDao = UserDao()
                    usersDao.adduser(user)

                    progressDialog.dismiss()
                    Toast.makeText(this,"signed up successfully", Toast.LENGTH_SHORT).show()
                      finish()
                     startActivity(Intent(this,MainActivity::class.java))

                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this,"something went wrong please try again", Toast.LENGTH_SHORT).show()
                }

            }
    }

    fun validateForm(name: String, email:String, password: String) : Boolean{

        return when{

            TextUtils.isEmpty(name)->{
               Toast.makeText(applicationContext,"Please enter a name",Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(email)->{
                Toast.makeText(applicationContext,"Please enter a email/username",Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(password)->{
                Toast.makeText(applicationContext,"Please enter a password",Toast.LENGTH_SHORT).show()
                false
            }
            else ->{
                true
            }
        }
    }
}