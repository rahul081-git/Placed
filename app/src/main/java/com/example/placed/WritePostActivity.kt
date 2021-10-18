package com.example.placed

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.placed.daos.PostDao
import com.example.placed.models.User
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_write_post.*
import java.util.*

class WritePostActivity : AppCompatActivity() {

    private lateinit var filePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        set_post_image.setOnClickListener {

            ImagePicker.with(this)
                .crop()
                .compress(1080)
                .maxResultSize(1080,1080)
                .start()
        }

        post_button.setOnClickListener {
            uploadFile()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {

            filePath = data.data!!

            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)

            write_post_image.setImageBitmap(bitmap)
        }
    }

    private fun uploadFile() {
        if (filePath != null) {
            var pd = ProgressDialog(this)
            pd.setTitle("Please wait")
            pd.show()

            var imageRef = FirebaseStorage.getInstance().reference.child("Post/"+UUID.randomUUID()+"pic.jpg")

            imageRef.putFile(filePath).addOnSuccessListener {
                pd.dismiss()
                Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                imageRef.downloadUrl.addOnSuccessListener {

                    val postText = post_edittext.text.toString().trim()
                    val postDao = PostDao()
                    val image = it.toString()

                    if (!postText.isEmpty()) {

                        postDao.addPost(postText,image)
                        Toast.makeText(this, "Post Added Successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }

            }.addOnFailureListener {
                pd.dismiss()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()

            }
        }
    }
}