package com.naval.trackingcovid.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.naval.trackingcovid.R
import com.naval.trackingcovid.db.DatabaseService
import com.naval.trackingcovid.model.User
import com.naval.trackingcovid.utils.LoadingDialog
import com.naval.trackingcovid.utils.Validation
import com.naval.trackingcovid.utils.Validation.Companion.both_inputs_correct
import com.naval.trackingcovid.utils.Validation.Companion.both_inputs_wrong
import com.naval.trackingcovid.utils.Validation.Companion.full_name_wrong
import com.naval.trackingcovid.utils.Validation.Companion.mobile_number_wrong
import kotlinx.android.synthetic.main.add_user_activity.*
import kotlinx.android.synthetic.main.take_reading_activity.*
import java.sql.RowId
import java.time.LocalDateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

class AddUserActivity : AppCompatActivity(){

    val TAG = "AddUserActivity"
    val dialogText = "Creating User..."
    lateinit var fullNameEditText : TextInputEditText
    lateinit var mobNumberEditText : TextInputEditText
    lateinit var user : User
    lateinit var covidDB : DatabaseService
    lateinit var toast: Toast


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_user_activity)
        Log.d(TAG,"In Add User Acitivity")
        covidDB = DatabaseService.getInstance(this)
        toast = Toast.makeText(this,"",Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER,Gravity.CENTER_HORIZONTAL,Gravity.CENTER_VERTICAL)
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

            if(validations()){
                val user = User(fullName = fullNameEditText.text.toString(),
                                mobileNo = mobNumberEditText.text.toString(),
                                createdDate = LocalDateTime.now())
                insertUserToDb(user)
                LoadingDialog.dismiss()
            }
            startTakingReadingButton.visibility = View.VISIBLE
        }

        startTakingReadingButton.setOnClickListener {
            val intent = Intent(this,TakeReadingActivity::class.java)
            startActivity(intent)
        }
    }


    private fun insertUserToDb(user: User) {
        val userDao = covidDB.userDao()
        val rowId = userDao.insertUser(user)
        LoadingDialog.show(this,dialogText)
        fullNameEditText.text?.clear()
        mobNumberEditText.text?.clear()
    }


    private fun validations():Boolean {

        val fullName = fullNameEditText.text.toString()
        val mobileNumber = mobNumberEditText.text.toString()

        val validationResult = Validation.validateUserCreationInput(fullName, mobileNumber)

        when(validationResult){
            0 -> return true
            1 -> {
                toast.setText(both_inputs_wrong)
                toast.show()
            }
            2 ->{
                toast.setText(full_name_wrong)
                toast.show()
            }
            3 -> {
                toast.setText(mobile_number_wrong)
                toast.show()
            }
        }

        return false
    }

}