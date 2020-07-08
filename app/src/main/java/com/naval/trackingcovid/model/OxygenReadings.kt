package com.naval.trackingcovid.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "Oxygen_Readings")
data class OxygenReadings(

    @PrimaryKey(autoGenerate = true)
    val id:Int,

    @ColumnInfo(name = "reading_date_and_time")
    val readingDateTime: LocalDateTime,

    @ColumnInfo(name = "oxygen_readings")
    val oxygenReadings: List<String>,

    @ColumnInfo(name = "owner_id")
    val userOwnerId:String
) {
}