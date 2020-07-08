package com.naval.trackingcovid.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.naval.trackingcovid.model.OxygenReadings

@Dao
interface OxygenReadingsDao {

    @Insert
    fun insertReading(oxygenReading: OxygenReadings)

    @Query("SELECT * FROM Oxygen_Readings")
    fun getAllReadings():List<OxygenReadings>

}