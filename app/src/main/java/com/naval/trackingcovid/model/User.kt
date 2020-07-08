package com.naval.trackingcovid.model

import androidx.room.*
import java.time.LocalDateTime


@Entity(tableName = "users",indices = arrayOf(Index(value = ["mobile_number"])))
data class User(

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @PrimaryKey
    @ColumnInfo(name = "mobile_number")
    val mobileNo:String,

    @ColumnInfo(name = "creation_date")
    val createdDate:LocalDateTime
) {


}