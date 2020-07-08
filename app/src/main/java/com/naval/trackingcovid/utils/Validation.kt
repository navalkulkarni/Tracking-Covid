package com.naval.trackingcovid.utils

import android.widget.Toast
import com.naval.trackingcovid.ui.AddUserActivity

class Validation {

    companion object{

        const val both_inputs_correct = "Valid Input"
        const val both_inputs_wrong = "Invalid Input"
        const val full_name_wrong = "Invalid Name"
        const val mobile_number_wrong = "Invalid Mobile Number"

        fun validateUserCreationInput(fullName:String,mobileNumber:String) : Int{
            // if both are not empty
            when (fullName.isNotEmpty() && mobileNumber.isNotEmpty()){
                //if both are incorrect
                !fullName.matches(Regex("[^0-9]{6,60}")) && !mobileNumber.matches(Regex("[6-9]{1}[0-9]{9}")) -> return 1
                //if name is incorrect
                !fullName.matches(Regex("[^0-9]{6,60}")) && mobileNumber.matches(Regex("[6-9]{1}[0-9]{9}")) -> return 2
                //if mobile number is incorrect
                !mobileNumber.matches(Regex("[6-9]{1}[0-9]{9}")) && fullName.matches(Regex("[^0-9]{6,60}")) -> return 3
                else -> return 0
            }

        }

    }

}