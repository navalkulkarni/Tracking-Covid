package com.naval.trackingcovid.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "mobile_number")
    val mobileNo:String,

    @ColumnInfo(name = "reading_list")
    val readingsList : Array<Int> = arrayOf(0,0,0,0,0)
) {

}