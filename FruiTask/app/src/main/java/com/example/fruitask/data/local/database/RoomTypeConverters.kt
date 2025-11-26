package com.example.fruitask.data.local.database

import androidx.room.TypeConverter
import com.example.fruitask.data.local.model.FruitType

class RoomTypeConverters {

    // Convierte el Enum FruitType a String para almacenarlo en la base de datos.
    @TypeConverter
    fun fromFruitType(type: FruitType): String {
        return type.name
    }

    // Convierte el String de la base de datos de vuelta al Enum FruitType.
    @TypeConverter
    fun toFruitType(typeString: String): FruitType {
        return FruitType.valueOf(typeString)
    }
}