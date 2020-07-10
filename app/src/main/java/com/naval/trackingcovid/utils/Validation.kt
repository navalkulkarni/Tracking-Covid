package com.naval.trackingcovid.utils

class Validation {

    companion object{

        const val both_inputs_correct = "Valid Input"
        const val both_inputs_wrong = "Invalid Input"
        const val full_name_wrong = "Invalid Name"
        const val mobile_number_wrong = "Invalid Mobile Number"
        const val temp_threshold = 35
        const val oxygen_threshold = 90

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

        fun validateReadingAndSendMail(tempReading: Int, oxygenReading: Int) : Boolean {

            if(tempReading > temp_threshold && oxygenReading < oxygen_threshold)
                return false
            return true
        }

    }

}