package com.example.fruitask.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarFruit(fruta: FruitEntity)

    @Update
    suspend fun updateFruit(fruta: FruitEntity)

    @Delete
    suspend fun deleteFruitById(fruitId: Int)

    @Query("SELECT * FROM fruit ORDER BY nombre ASC")
    fun getAllFruits(): Flow<List<FruitEntity>>

    @Query("SELECT * FROM fruit WHERE id = :fruitId")
    suspend fun getFruitById(fruitId: Int): FruitEntity?
}