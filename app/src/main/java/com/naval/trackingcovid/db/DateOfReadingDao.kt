package com.naval.trackingcovid.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naval.trackingcovid.model.DateOfReading
import java.time.LocalDate

@Dao
interface DateOfReadingDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertTodaysDate(dateOfReading: DateOfReading)

    @Query("SELECT * FROM date_of_reading WHERE reading_date = :inputDate")
    fun getDate(inputDate:LocalDate): DateOfReading

    @Query("DELETE FROM date_of_reading")
    fun deleteAllDateOfReading()
}