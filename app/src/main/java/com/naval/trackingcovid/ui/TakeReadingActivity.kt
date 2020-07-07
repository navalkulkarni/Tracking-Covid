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
import kotlinx.android.synthetic.main.take_reading_activity.*


class TakeReadingActivity : AppCompatActivity() {

    lateinit var mobileNumberEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.take_reading_activity)
        bindViews()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        searchUserButton.setOnClickListener {
            val query = mobileNumberEditText.text.toString()
            Log.d("TakeReadingActivity",query)
        }
    }

    private fun bindViews() {
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText)
    }


}