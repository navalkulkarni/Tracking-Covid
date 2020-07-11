package com.naval.trackingcovid.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.sql.Date
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalDateTime.ofInstant
import java.time.ZoneOffset
import java.util.*

class Converters{


    companion object{


        @TypeConverter
        @JvmStatic
        fun fromString(value:String):List<String>{
            val listType = object : TypeToken<List<String>>(){}.type
            return Gson().fromJson(value,listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromArray(value:List<String>):String{
            val gson = Gson()
            val json = gson.toJson(value)
            return json
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?): LocalDateTime? {
            return value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC) }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: LocalDateTime?): Long? {
            return date?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        @JvmStatic
        fun fromTimestampToLocalDate(value: Long?): LocalDate? {
            return value?.let { LocalDate.ofEpochDay(value) }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        @JvmStatic
        fun localDateToTimestamp(date: LocalDate?): Long? {
            return date?.toEpochDay()
        }

    }

}