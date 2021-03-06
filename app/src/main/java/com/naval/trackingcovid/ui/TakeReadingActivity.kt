package com.naval.trackingcovid.ui


import android.os.Build
import android.os.Bundle
import android.transition.Visibility
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.naval.trackingcovid.R
import com.naval.trackingcovid.db.DatabaseService
import com.naval.trackingcovid.model.OxygenReadings
import com.naval.trackingcovid.model.User
import com.naval.trackingcovid.utils.LoadingDialog
import com.naval.trackingcovid.utils.MailSender
import com.naval.trackingcovid.utils.Validation
import kotlinx.android.synthetic.main.take_reading_activity.*
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class TakeReadingActivity : AppCompatActivity() {

    val TAG = "TakeReadingActivity"
    val dialogTextForReading:String = "Saving Reading..."
    val dialogTextForSearchUser:String = "Searching..."
    lateinit var mobileNumberEditText: EditText
    lateinit var oxygenReadingEditText: EditText
    lateinit var tempReadingEditText: EditText
    lateinit var userInfoTextView: TextView
    @RequiresApi(Build.VERSION_CODES.O)
    var user:User? = User("","", LocalDateTime.now())

    lateinit var toast: Toast
    lateinit var covidDB : DatabaseService

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.take_reading_activity)
        covidDB = DatabaseService.getInstance(this)
        toast = Toast.makeText(this,"User Does Not Exist",Toast.LENGTH_LONG)
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
                LoadingDialog.dismiss()
                if(!result){
                    MailSender.sendEmailToAuthorities(this,tempReading.toInt(),oxygenReading.toInt(),user,dateTime)
                }
                clearTextViews()
            }
            else {
                readingListOfUser.readingDateTime = dateTime
                if (readingListOfUser.temperatureReadings.size >= 4 || readingListOfUser.oxygenReadings.size >= 4)
                    hideReadingEditText()
                else {
                    readingListOfUser.temperatureReadings.add(tempReading)
                    readingListOfUser.oxygenReadings.add(oxygenReading)
                    covidDB.oxygenReadingDao().updateReading(readingListOfUser)
                    LoadingDialog.show(this,dialogTextForReading)
                    LoadingDialog.dismiss()
                    val size = setReadingLeftTextView(readingListOfUser)
                    readingRemainingTextView.text = "$size readings left"
                    if (!result) {
                        MailSender.sendEmailToAuthorities(
                            this,
                            tempReading.toInt(),
                            oxygenReading.toInt(),
                            user,
                            dateTime
                        )
                    }
                    clearTextViews()

                }
            }

        }
    }

    private fun hideReadingEditText() {
        Toast.makeText(this,
            "No more readings allowed for today for this user",Toast.LENGTH_LONG).show()
        tempReadingEditText.visibility = View.GONE
        oxygenReadingEditText.visibility = View.GONE

    }



    private fun clearTextViews() {
        userInfoTextView.text = ""
        mobileNumberEditText.text.clear()
        tempReadingEditText.text.clear()
        oxygenReadingEditText.text.clear()
        readingRemainingTextView.text = ""
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
            try {
                 user = covidDB.userDao().getUserByMobileNumber(query)
            }catch (e: IllegalArgumentException)
            {
                e.printStackTrace()
            }

            if(user!=null)
            {
                var readingListOfUser:OxygenReadings? = findReadingListOfUser(user)
                if(readingListOfUser?.oxygenReadings == null)
                    size = "5"
                else{
                    size = setReadingLeftTextView(readingListOfUser)
                }
                LoadingDialog.show(this,dialogTextForSearchUser)

                showReadingView(size,
                    LocalDateTime.now().
                    format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))!!)
            }else
            {
                toast.setGravity(Gravity.CENTER,Gravity.CENTER_HORIZONTAL,Gravity.CENTER_VERTICAL)
                toast.show()

            }

        }

    }

    private fun showReadingView(size: String, format: String) {
        LoadingDialog.dismiss()
        readingRemainingTextView.text = "$size readings left for today"
        readingRemainingTextView.visibility = View.VISIBLE
        userInfoTextView.visibility = View.VISIBLE
        userInfoTextView.text = "You are taking reading of ${user?.fullName}  at ${format}"
        readingEditText.visibility = View.VISIBLE
        confirmReadingButton.visibility = View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun findReadingListOfUser(user: User?) : OxygenReadings? {

         var listFound: OxygenReadings? = null
        val userWithOxygenReadings = covidDB.userWithOxygenReadingsDao().getUsersWithReadings()
        userWithOxygenReadings.forEach {
            it.oxygenReadingsList.forEach {
                if(it.userOwnerId == user?.mobileNo)
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
            user?.mobileNo, LocalDate.now())
        covidDB.oxygenReadingDao().insertReading(firstList)
        LoadingDialog.show(this,dialogTextForReading)
        val size = setReadingLeftTextView(firstList)
        readingRemainingTextView.text =  "$size readings left for today"
    }

    private fun bindViews() {
        userInfoTextView = findViewById(R.id.userInfoTextView)
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText)
        tempReadingEditText = findViewById(R.id.tempReadingEditText)
        oxygenReadingEditText = findViewById(R.id.readingEditText)
    }


}