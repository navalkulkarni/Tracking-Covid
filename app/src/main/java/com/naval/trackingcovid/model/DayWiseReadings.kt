package com.naval.trackingcovid.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "DayWiseReading")
data class DayWiseReadings(

    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: LocalDate,

    @Embedded
    val user: User

) {
}