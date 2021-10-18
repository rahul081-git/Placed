package com.example.placed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        forgot_password_send_btn.setOnClickListener {

            Firebase.auth.sendPasswordResetEmail(forgot_password_username_edittext.text.toString())
                .addOnCompleteListener {task->

                    if(task.isSuccessful) {
                        Toast.makeText(this, "password reset link is sent", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }
}