package com.naval.trackingcovid.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.naval.trackingcovid.model.UserWithOxygenReadings

@Dao
interface UserWithOxygenReadingsDao {

    @Transaction
    @Query("SELECT * FROM users")
    fun getUsersWithReadings():List<UserWithOxygenReadings>

}