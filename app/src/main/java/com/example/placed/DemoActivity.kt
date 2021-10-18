package com.example.placed


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore

import com.github.dhaval2404.imagepicker.ImagePicker

import kotlinx.android.synthetic.main.activity_demo.*
import kotlinx.android.synthetic.main.activity_user_profile.*


class DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        val frg = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_container,frg).commit()


        btm_nev_view.setOnNavigationItemSelectedListener { item->

            when(item.itemId){
                R.id.dashboard -> {
                    val frg = MainFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_container,frg).commit()
                }
                R.id.people -> {
                    val frg = PlacedPeopleFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_container,frg).commit()
                }
                R.id.create -> {
                    val frg = CreateOrAddFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_container,frg).commit()
                }
                R.id.questions -> {
                    val frg = InterViewQuestionsFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.main_container,frg).commit()
                }
                R.id.profile -> {
                    startActivity(Intent(this,UserProfileActivity::class.java))
                }

            }
            true
        }

    }

}
