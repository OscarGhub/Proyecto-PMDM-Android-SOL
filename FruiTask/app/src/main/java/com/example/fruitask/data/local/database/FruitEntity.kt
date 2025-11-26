package com.example.fruitask.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fruitask.data.model.FruitType

@Entity(tableName = "fruit")
data class FruitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val tipo: FruitType,
    val nivel: Int,
    val experiencia: Double
)
