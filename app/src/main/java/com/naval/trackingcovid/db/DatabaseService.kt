package com.naval.trackingcovid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.naval.trackingcovid.model.DayWiseReadings
import com.naval.trackingcovid.model.User

@Database(entities = [User::class,DayWiseReadings::class],version = 1)
abstract class DatabaseService : RoomDatabase() {

    abstract fun userDao():UserDao
    abstract fun dayWiseReadingDao():DayWiseReadingsDao

    companion object{
        lateinit var INSTANCE : DatabaseService
        fun getInstance(context: Context):DatabaseService{
            synchronized(DatabaseService::class){
                INSTANCE = Room.databaseBuilder(context.applicationContext,DatabaseService::class.java,"my-readings.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }

}