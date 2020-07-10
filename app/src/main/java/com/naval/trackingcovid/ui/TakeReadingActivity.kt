package com.naval.trackingcovid.ui


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import com.naval.trackingcovid.R
import com.naval.trackingcovid.db.DatabaseService
import com.naval.trackingcovid.model.OxygenReadings
import com.naval.trackingcovid.model.User
import com.naval.trackingcovid.utils.MailSender
import com.naval.trackingcovid.utils.Validation
import kotlinx.android.synthetic.main.take_reading_activity.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class TakeReadingActivity : AppCompatActivity() {

    val TAG = "TakeReadingActivity"
    val TAG2 = "TakeActivityInIfBlock"
    lateinit var mobileNumberEditText: EditText
    lateinit var oxygenReadingEditText: EditText
    lateinit var tempReadingEditText: EditText
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
            val tempReading : String = tempReadingEditText.text.toString()
            val oxygenReading = oxygenReadingEditText.text.toString()

            var readingListOfUser:OxygenReadings? = findReadingListOfUser(user)
            val result: Boolean =
                Validation.validateReadingAndSendMail(tempReading.toInt(),oxygenReading.toInt())
            if(readingListOfUser == null)
            {
                insertReadingToDB()
                if(!result){
                    MailSender.sendEmailToAuthorities(this,tempReading.toInt(),oxygenReading.toInt(),user,dateTime)
                }
            }
            else{
                readingListOfUser.readingDateTime = dateTime
                readingListOfUser.temperatureReadings.add(tempReading)
                readingListOfUser.oxygenReadings.add(oxygenReading)
                covidDB.oxygenReadingDao().updateReading(readingListOfUser)
                Log.d(TAG,findReadingListOfUser(user).toString())
                val size =setReadingLeftTextView(readingListOfUser)
                readingRemainingTextView.text =  "$size readings left"
                if(!result){
                    MailSender.sendEmailToAuthorities(this,tempReading.toInt(),oxygenReading.toInt(),user,dateTime)
                }
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
                showReadingView(size,
                    LocalDateTime.now().
                    format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))!!)
        }
    }

    private fun showReadingView(size: String, format: String) {
        readingRemainingTextView.text = "$size readings left"
        readingRemainingTextView.visibility = View.VISIBLE
        userInfoTextView.visibility = View.VISIBLE
        userInfoTextView.text = "You are taking reading of ${user.fullName}  at ${format}"
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertReadingToDB()
    {
        val dateTime = LocalDateTime.now()
        val readingListForFirstTimeUser: MutableList<String> =
            mutableListOf(readingEditText.text.toString())
        val tempReadingListForFirstTimeUser: MutableList<String> =
            mutableListOf(tempReadingEditText.text.toString())
        val firstList = OxygenReadings(0,dateTime,
            tempReadingListForFirstTimeUser,
            readingListForFirstTimeUser,
            user.mobileNo)
        covidDB.oxygenReadingDao().insertReading(firstList)
        val size = setReadingLeftTextView(firstList)
        Log.d(TAG,findReadingListOfUser(user).toString())
        readingRemainingTextView.text =  "$size readings left"
    }

    private fun bindViews() {
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText)
        tempReadingEditText = findViewById(R.id.tempReadingEditText)
        oxygenReadingEditText = findViewById(R.id.readingEditText)
    }


}