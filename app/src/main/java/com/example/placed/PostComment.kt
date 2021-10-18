package com.example.placed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.placed.daos.CommentDao
import com.example.placed.models.Comments
import com.example.placed.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list_of_placed_people.*
import kotlinx.android.synthetic.main.activity_post_comment.*

class PostComment : AppCompatActivity() {

    private lateinit var postId : String
    private lateinit var adapter : CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_comment)

        postId = intent.getStringExtra("postId").toString()
        setRecyclerView()

        btn_post_comment.setOnClickListener {
            val text = write_comment_edittext.text.toString()
            CommentDao().addComment(text,postId)

            write_comment_edittext.setText("")

            Toast.makeText(this,"successfully commented",Toast.LENGTH_SHORT).show()
        }
    }



    private fun setRecyclerView() {
        val al = ArrayList<Comments>()
        adapter = CommentAdapter(al)
        comment_recycler_view.adapter= adapter
        comment_recycler_view.layoutManager= LinearLayoutManager(this)

       FirebaseFirestore.getInstance()
               .collection("comments")
               .document(postId)
               .collection("Publisher")
               .get()
               .addOnSuccessListener {

                   for(doc in it){
                       val cmnt = doc.toObject(Comments::class.java)
                       al.add(cmnt)
                   }
                   adapter.notifyDataSetChanged()


       }
    }
}