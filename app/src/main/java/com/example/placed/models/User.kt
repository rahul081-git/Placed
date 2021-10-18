package com.example.placed.models

class User(val uid : String = "",
            val displayName : String? = "",
            val profileImage : String? = "",
           val backgroundImage : String? = "",
            val instituteName :String?="",
           val company :String?="",
           val branch :String?="",
            val batch : String?="",
            val resume: String = "",
           val isRegistered : Boolean = false)
