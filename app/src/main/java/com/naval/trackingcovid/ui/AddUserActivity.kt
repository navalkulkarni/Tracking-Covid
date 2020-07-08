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
import com.naval.trackingcovid.utils.Validation
import com.naval.trackingcovid.utils.Validation.Companion.both_inputs_correct
import com.naval.trackingcovid.utils.Validation.Companion.both_inputs_wrong
import com.naval.trackingcovid.utils.Validation.Companion.full_name_wrong
import com.naval.trackingcovid.utils.Validation.Companion.mobile_number_wrong
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
                insertUserToDb(user)
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

        val validationResult = Validation.validateUserCreationInput(fullName, mobileNumber)
        Log.d(TAG, validationResult.toString())
        when(validationResult){
            0 -> {
                Toast.makeText(this,both_inputs_correct,Toast.LENGTH_LONG).show()
                return true}
            1 -> Toast.makeText(this,both_inputs_wrong,Toast.LENGTH_LONG).show()
            2 -> Toast.makeText(this,full_name_wrong,Toast.LENGTH_LONG).show()
            3 -> Toast.makeText(this, mobile_number_wrong,Toast.LENGTH_LONG).show()
        }

        return false
    }

}