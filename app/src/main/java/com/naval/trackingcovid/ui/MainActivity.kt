package com.naval.trackingcovid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naval.trackingcovid.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupClickListeners()

    }

    private fun setupClickListeners() {
        addUserButton.setOnClickListener {
            val intent = Intent(this@MainActivity,AddUserActivity::class.java)
            startActivity(intent)
        }

        takeReadingButton.setOnClickListener {
            val intent = Intent(this@MainActivity,TakeReadingActivity::class.java)
            startActivity(intent)
        }
    }


}