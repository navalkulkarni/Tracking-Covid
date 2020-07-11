package com.naval.trackingcovid.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "Oxygen_Readings")
data class OxygenReadings(

    @PrimaryKey(autoGenerate = true)
    val id:Int,

    @ColumnInfo(name = "reading_date_and_time")
    var readingDateTime: LocalDateTime,

    @ColumnInfo(name="temperature_reading")
    val temperatureReadings: MutableList<String>,

    @ColumnInfo(name = "oxygen_readings")
    val oxygenReadings: MutableList<String>,

    @ColumnInfo(name = "owner_id")
    val userOwnerId: String?,

    @ColumnInfo(name="owner_date")
    val date : LocalDate
) {
}