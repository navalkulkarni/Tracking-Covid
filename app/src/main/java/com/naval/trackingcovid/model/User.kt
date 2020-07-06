package com.naval.trackingcovid.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



data class User(


    val id: String,


    val fullName: String,


    val mobileNo:String
) {
}