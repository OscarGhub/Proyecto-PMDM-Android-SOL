package com.example.fruitask.data.local.database

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }
}
