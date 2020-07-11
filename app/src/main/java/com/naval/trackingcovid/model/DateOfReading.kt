package com.naval.trackingcovid.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "date_of_reading")
data class DateOfReading(

    @PrimaryKey
    @ColumnInfo(name = "reading_date")
    val dateOfReading : LocalDate
) {
}