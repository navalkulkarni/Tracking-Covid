package com.naval.trackingcovid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naval.trackingcovid.R
import com.naval.trackingcovid.db.DatabaseService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var covidDB : DatabaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        covidDB = DatabaseService.getInstance(this)
        covidDB.userDao().deleteAllUsers()
        covidDB.oxygenReadingDao().deleteAllReadings()
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