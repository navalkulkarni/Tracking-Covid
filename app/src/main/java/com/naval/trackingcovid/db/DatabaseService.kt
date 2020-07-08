package com.naval.trackingcovid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.naval.trackingcovid.model.OxygenReadings
import com.naval.trackingcovid.model.User

@Database(entities = [User::class,OxygenReadings::class],version = 2)
@TypeConverters(Converters::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun userDao():UserDao
    abstract fun oxygenReadingDao():OxygenReadingsDao

    companion object{
        lateinit var INSTANCE : DatabaseService
        fun getInstance(context: Context):DatabaseService{
            synchronized(DatabaseService::class){
                INSTANCE = Room.databaseBuilder(context.applicationContext,DatabaseService::class.java,"my-readings.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }

}