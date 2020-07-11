package com.naval.trackingcovid.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.naval.trackingcovid.R
import com.naval.trackingcovid.db.DatabaseService
import com.naval.trackingcovid.model.DateOfReading
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class MainActivity : AppCompatActivity() {


    lateinit var covidDB : DatabaseService


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dateOfReading = DateOfReading(LocalDate.now())
        covidDB = DatabaseService.getInstance(this)
        //covidDB.dateOfReadingDao().deleteAllDateOfReading()
        checkAndInsertTodaysDate()

        covidDB.userDao().deleteAllUsers()
        covidDB.oxygenReadingDao().deleteAllReadings()
        setupClickListeners()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkAndInsertTodaysDate() {
        val dateOfReading = covidDB.dateOfReadingDao().getDate(LocalDate.now())
        if(dateOfReading == null)
            covidDB.dateOfReadingDao().insertTodaysDate(DateOfReading(LocalDate.now()))
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