package com.naval.trackingcovid.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.naval.trackingcovid.R
import com.naval.trackingcovid.db.DatabaseService
import com.naval.trackingcovid.model.User
import kotlinx.android.synthetic.main.add_user_activity.*
import java.time.LocalDateTime

class AddUserActivity : AppCompatActivity(){

    val TAG = "AddUserActivity"
    lateinit var fullNameEditText : TextInputEditText
    lateinit var mobNumberEditText : TextInputEditText
    lateinit var user : User
    lateinit var covidDB : DatabaseService


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_user_activity)
        Log.d(TAG,"In Add User Acitivity")
        covidDB = DatabaseService.getInstance(this)
        bindViews()
        setupClickListeners()

    }

    private fun bindViews() {
        fullNameEditText = findViewById(R.id.fullNameEditText)
        mobNumberEditText = findViewById(R.id.mobNumberEditText)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupClickListeners() {
        createUserButton.setOnClickListener {
            startTakingReadingButton.visibility = View.VISIBLE
            if(validations()){
                val user = User(fullName = fullNameEditText.text.toString(),
                                mobileNo = mobNumberEditText.text.toString(),
                                createdDate = LocalDateTime.now())
                //insertUserToDb(user)
            }
        }

        startTakingReadingButton.setOnClickListener {
            val intent = Intent(this,TakeReadingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun insertUserToDb(user: User) {

        val userDao = covidDB.userDao()

        Log.d(TAG,userDao.insertUser(user).toString())
    }


    private fun validations():Boolean {

        val fullName = fullNameEditText.text.toString()
        val mobileNumber = mobNumberEditText.text.toString()

        // if both are not empty
        when (fullName.isNotEmpty() && mobileNumber.isNotEmpty()){
            //if both are incorrect
            !fullName.matches(Regex("[^0-9]{6,60}")) && !mobileNumber.matches(Regex("[6-9]{1}[0-9]{9}")) -> Toast.makeText(this,"Invalid Input",Toast.LENGTH_LONG).show()
            //if name is incorrect
            !fullName.matches(Regex("[^0-9]{6,60}")) && mobileNumber.matches(Regex("[6-9]{1}[0-9]{9}")) ->Toast.makeText(this,"Invalid Name",Toast.LENGTH_LONG).show()
            //if mobile number is incorrect
            !mobileNumber.matches(Regex("[6-9]{1}[0-9]{9}")) && fullName.matches(Regex("[^0-9]{6,60}")) ->Toast.makeText(this,"Invalid Mobile Number",Toast.LENGTH_LONG)  .show()
            else -> return true
        }

        return false
    }

}