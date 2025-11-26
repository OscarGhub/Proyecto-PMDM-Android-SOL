package com.example.fruitask.data.local.database

import androidx.room.TypeConverter
import com.example.fruitask.data.local.model.FruitType

class RoomTypeConverters {

    // Convierte el enum a String para guardarlo en SQLite
    @TypeConverter
    fun fromFruitType(type: FruitType): String {
        return type.name
    }

    // Convierte la String de SQLite de vuelta al enum
    @TypeConverter
    fun toFruitType(name: String): FruitType {
        return FruitType.valueOf(name)
    }
}