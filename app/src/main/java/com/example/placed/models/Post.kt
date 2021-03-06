package com.example.placed.models

class Post(
        val text : String = "",
        val image : String?="",
        val createdBy : User = User(),
        val createdAt : Long = 0L,
        val likedBy: ArrayList<String> = ArrayList()
)