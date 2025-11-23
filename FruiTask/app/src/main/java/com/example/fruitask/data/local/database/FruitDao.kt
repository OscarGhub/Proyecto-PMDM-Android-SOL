package com.example.fruitask.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarFruit(fruta: FruitEntity)

    @Query("SELECT * FROM fruit ORDER BY nombre ASC")
    fun getAllFruits(): Flow<List<FruitEntity>>

    @Query("SELECT * FROM fruit WHERE id = :fruitId")
    suspend fun getFruitById(fruitId: Int): FruitEntity?
}