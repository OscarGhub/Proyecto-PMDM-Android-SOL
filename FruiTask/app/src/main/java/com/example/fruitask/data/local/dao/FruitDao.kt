package com.example.fruitask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fruitask.data.local.entity.FruitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertarFruit(fruta: FruitEntity)

    @Query("SELECT * FROM fruit ORDER BY nombre ASC")
    fun getAllFruits(): Flow<List<FruitEntity>>

    @Query("SELECT * FROM fruit WHERE id = :fruitId")
    suspend fun getFruitById(fruitId: Int): FruitEntity?
}