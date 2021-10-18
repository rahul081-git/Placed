package com.example.placed

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_user_sign_up.*

class IntroActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        mAuth = Firebase.auth

        if(mAuth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }


        signin_button.setOnClickListener {

            if(validateForm(signin_username_edittext.text.toString().trim(),signin_password_edittext.text.toString().trim())) {
                signIn(signin_username_edittext.text.toString().trim(),signin_password_edittext.text.toString().trim())
            }
        }

        intro_forgot_password_text_view.setOnClickListener {
            startActivity(Intent(this,ForgotPassword::class.java))
        }

    }

    fun userSignUp(view: View) {
        startActivity(Intent(this,UserSignUpActivity::class.java))
    }

    fun signIn(email: String,password: String){

        var progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Signing In")
        progressDialog.show()

        mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this)  { task->
                        if(task.isSuccessful){
                            progressDialog.dismiss()
                           Toast.makeText(this,"signed in successfully",Toast.LENGTH_SHORT).show()

                            startActivity(Intent(this,MainActivity::class.java))
                        } else {
                            progressDialog.dismiss()
                            Toast.makeText(this, "something went wrong",Toast.LENGTH_SHORT).show()
                   }
        }
    }
    fun validateForm(email:String, password: String) : Boolean{

        return when{

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