package com.naval.trackingcovid.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.naval.trackingcovid.R
import com.naval.trackingcovid.db.DatabaseService
import com.naval.trackingcovid.model.User
import kotlinx.android.synthetic.main.take_reading_activity.*


class TakeReadingActivity : AppCompatActivity() {

    val TAG = "TakeReadingActivity"
    lateinit var mobileNumberEditText: EditText
    lateinit var user : User
    lateinit var covidDB : DatabaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.take_reading_activity)
        covidDB = DatabaseService.getInstance(this)
        bindViews()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        searchUserButton.setOnClickListener {
            val query = mobileNumberEditText.text.toString()
            user = covidDB.userDao().getUserByMobileNumber(query)
            Log.d(TAG,user.toString())
        }
    }

    private fun bindViews() {
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText)
    }


}