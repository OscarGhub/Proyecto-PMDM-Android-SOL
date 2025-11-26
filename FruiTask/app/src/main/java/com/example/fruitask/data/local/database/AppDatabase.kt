package com.example.fruitask.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fruitask.data.local.dao.FruitDao
import com.example.fruitask.data.local.dao.TaskDao
import com.example.fruitask.data.local.entity.FruitEntity

@Database(entities = [FruitEntity::class], version = 1, exportSchema = false)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun fruitDao(): FruitDao
    abstract fun TaskDao(): TaskDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "frutas_sol"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}