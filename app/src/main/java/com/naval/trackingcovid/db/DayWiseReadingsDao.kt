package com.naval.trackingcovid.db

import androidx.room.Dao
import androidx.room.Query
import com.naval.trackingcovid.model.DayWiseReadings

@Dao
interface DayWiseReadingsDao {

    @Query("SELECT * FROM DayWiseReading")
    fun getAllReadingsForToday() : List<DayWiseReadings>

}