package com.example.fruitask.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fruitask.data.local.model.FruitType

@Entity(tableName = "fruit")
data class FruitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val tipo: FruitType,
    var nivel: Integer,
    var experiencia: Double
)