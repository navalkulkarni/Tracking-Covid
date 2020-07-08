package com.naval.trackingcovid.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.naval.trackingcovid.model.User
import java.sql.RowId

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers():List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User):Long

    @Query("DELETE FROM users")
    fun deleteAllUsers()

    @Query("SELECT * FROM users WHERE mobile_number=:mobileNumber")
    fun getUserByMobileNumber( mobileNumber:String):User

}