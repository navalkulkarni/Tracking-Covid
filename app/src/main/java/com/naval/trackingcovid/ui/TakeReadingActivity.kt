package com.naval.trackingcovid.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.naval.trackingcovid.R
import com.naval.trackingcovid.db.DatabaseService
import com.naval.trackingcovid.model.OxygenReadings
import com.naval.trackingcovid.model.User
import kotlinx.android.synthetic.main.take_reading_activity.*
import java.time.LocalDateTime


class TakeReadingActivity : AppCompatActivity() {

    val TAG = "TakeReadingActivity"
    val TAG2 = "TakeActivityInIfBlock"
    lateinit var mobileNumberEditText: EditText
    lateinit var readingEditText: EditText
    lateinit var user : User
    lateinit var covidDB : DatabaseService

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.take_reading_activity)
        covidDB = DatabaseService.getInstance(this)
        bindViews()
        setupClickListenerForSearchUser()
        setupClickListenerForConfirmReading()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupClickListenerForConfirmReading() {
        confirmReadingButton.setOnClickListener {
            val dateTime = LocalDateTime.now()
            val reading = readingEditText.text.toString()

            var readingListOfUser:OxygenReadings? = findReadingListOfUser(user)
            if(readingListOfUser == null)
            {
                val readingListForFirstTimeUser: MutableList<String> =
                    mutableListOf(readingEditText.text.toString())
                val firstList =
                    OxygenReadings(0,dateTime,readingListForFirstTimeUser,user.mobileNo)
                covidDB.oxygenReadingDao().insertReading(firstList)
                val size = setReadingLeftTextView(firstList)
                readingRemainingTextView.text =  "$size readings left"

            }
            else{

                readingListOfUser.oxygenReadings.add(reading)
                covidDB.oxygenReadingDao().updateReading(readingListOfUser)
                val size =setReadingLeftTextView(readingListOfUser)
                readingRemainingTextView.text =  "$size readings left"

            }
        }
    }


    private fun setReadingLeftTextView(firstList: OxygenReadings):String {
        var size = 5;
        firstList.oxygenReadings.forEach {
            if(it != null)
                size--
        }
        return size.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupClickListenerForSearchUser() {
        searchUserButton.setOnClickListener {
            val query = mobileNumberEditText.text.toString()
            var size = ""
            user = covidDB.userDao().getUserByMobileNumber(query)
            var readingListOfUser:OxygenReadings? = findReadingListOfUser(user)
            if(readingListOfUser?.oxygenReadings == null)
                 size = "5"
            else{
                size = setReadingLeftTextView(readingListOfUser)
            }
            if(user!= null)
                showReadingView(size)
        }
    }

    private fun showReadingView(size:String) {
        readingRemainingTextView.text = "$size readings left"
        readingRemainingTextView.visibility = View.VISIBLE
        userInfoTextView.visibility = View.VISIBLE
        userInfoTextView.text = user.fullName
        readingEditText.visibility = View.VISIBLE
        confirmReadingButton.visibility = View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun findReadingListOfUser(user: User) : OxygenReadings? {

         var listFound: OxygenReadings? = null
        val userWithOxygenReadings = covidDB.userWithOxygenReadingsDao().getUsersWithReadings()
        userWithOxygenReadings.forEach {
            it.oxygenReadingsList.forEach {
                if(it.userOwnerId == user.mobileNo)
                    listFound = it
            }
        }
        return listFound
    }

    private fun bindViews() {
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText)
        readingEditText = findViewById(R.id.readingEditText)
    }


}