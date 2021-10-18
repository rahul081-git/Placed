package com.example.placed


import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem

import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import com.example.placed.models.User
import com.github.dhaval2404.imagepicker.ImagePicker

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_user_profile.*


class UserProfileActivity : AppCompatActivity() {
    lateinit var filePath : Uri
    lateinit var mAuth : FirebaseAuth
    private lateinit var currentUserImage: String
    private var isUpdateUserBtn:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        update_profile_image.setOnClickListener {
            isUpdateUserBtn=true;
            startFileChooser()
        }

        user_profile_background_btn.setOnClickListener {
            isUpdateUserBtn=false
            startFileChooser()
        }

        mAuth = Firebase.auth


        FirebaseFirestore.getInstance()
                .collection("users")
                .document(mAuth.currentUser!!.uid)
                .get().addOnSuccessListener {

                    val user = it.toObject(User::class.java)!!
                    user_name_prifile.text = user.displayName
                    user_institute_profile.text = user.instituteName
                    user_company_profile.text=user.company
                    Glide.with(this).load(user.profileImage).into(profile_circular_image_view)
                    Glide.with(this).load(user.backgroundImage).into(user_profile_background_image)

                }

   // Bottom Nevigation Bar Starts
        bottom_nevigation_view.selectedItemId = R.id.profile

        bottom_nevigation_view.setOnNavigationItemSelectedListener { item->

            when(item.itemId){

                R.id.dashboard -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                }
                R.id.people -> {
                    startActivity(Intent(this, PlacedPeopleActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                }
                R.id.create -> {
                    startActivity(Intent(this, CreateOrAddActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                }
                R.id.questions -> {
                    startActivity(Intent(this, InterViewQuestionsActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                }
                R.id.profile -> {

                    true
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
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
                true
            }
            R.id.delete_account -> {

                val alertDialog = AlertDialog.Builder(this)

                alertDialog.setTitle("Delete Account")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes") { _, _ ->
                            deleteAccount(mAuth)

                        }
                        .setNegativeButton("No") { _, _ ->


                        }.show()
                true
            }
            R.id.notification-> {
                startActivity(Intent(this,NotificationActivity::class.java))
                true
            }
            else ->{
                false
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteAccount(user: FirebaseAuth){

        user.currentUser!!.delete()
            .addOnCompleteListener{ task->
                if (task.isSuccessful){
                    Toast.makeText(applicationContext, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                    finish()
                    startActivity(Intent(applicationContext, IntroActivity::class.java))
                }

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
            uploadFile()


        }
    }  private fun uploadFile() {
        if(filePath!=null){
            var pd = ProgressDialog(this)
            pd.setTitle("Uploading")
            pd.show()

            if(isUpdateUserBtn) {

                var imageRef = FirebaseStorage.getInstance().reference.child("User/" + mAuth.currentUser!!.uid + "/ProfileImage/pic.jpg")

                imageRef.putFile(filePath).addOnSuccessListener {
                    pd.dismiss()
                    Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                    imageRef.downloadUrl.addOnSuccessListener {

                        val hmp = hashMapOf("profileImage" to it.toString())
                        FirebaseFirestore.getInstance().collection("users").document(mAuth.currentUser!!.uid).set(hmp, SetOptions.merge())

                        FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(mAuth.currentUser!!.uid)
                                .get().addOnSuccessListener {

                                    val user = it.toObject(User::class.java)!!

                                    currentUserImage = user.profileImage.toString()

                                    Glide.with(this).load(user.profileImage).into(profile_circular_image_view)

                                }
                    }

                }.addOnFailureListener {
                    pd.dismiss()
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()

                }.addOnProgressListener {

                    val progress: Double = (100.0 * it.bytesTransferred) / it.totalByteCount
                    pd.setMessage("${progress.toInt()}% Uploaded")

                }
            } else {
                var imageRef = FirebaseStorage.getInstance().reference.child("User/" + mAuth.currentUser!!.uid + "/BackgroundImage/pic.jpg")

                imageRef.putFile(filePath).addOnSuccessListener {
                    pd.dismiss()
                    Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                    imageRef.downloadUrl.addOnSuccessListener {

                        val hmp = hashMapOf("backgroundImage" to it.toString())
                        FirebaseFirestore.getInstance().collection("users").document(mAuth.currentUser!!.uid).set(hmp, SetOptions.merge())

                        FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(mAuth.currentUser!!.uid)
                                .get().addOnSuccessListener {

                                    val user = it.toObject(User::class.java)!!

                                    currentUserImage = user.backgroundImage.toString()

                                    Glide.with(this).load(currentUserImage).into(user_profile_background_image)

                                }
                    }

                }.addOnFailureListener {
                    pd.dismiss()
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()

                }.addOnProgressListener {

                    val progress: Double = (100.0 * it.bytesTransferred) / it.totalByteCount
                    pd.setMessage("${progress.toInt()}% Uploaded")

                }
            }
        }
    }

}