package com.naval.trackingcovid.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.naval.trackingcovid.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers():List<User>

    @Insert(onConflict = REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE mobile_number=:mobileNumber")
    fun getUserByMobileNumber( mobileNumber:String):User

}