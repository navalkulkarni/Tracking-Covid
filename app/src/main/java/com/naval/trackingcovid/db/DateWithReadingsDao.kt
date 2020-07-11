package com.naval.trackingcovid.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.naval.trackingcovid.model.DateWithReadings

@Dao
interface DateWithReadingsDao {

    @Transaction
    @Query("SELECT * FROM date_of_reading")
    fun getAllDaysWithReadings(): MutableList<DateWithReadings>

}