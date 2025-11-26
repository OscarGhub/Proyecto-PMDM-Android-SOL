package com.example.fruitask.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.fruitask.data.local.model.TipoActividad
import java.util.Date

@Entity(tableName = "task", foreignKeys = [

    ForeignKey(entity = com.example.fruitask.data.local.entity.FruitEntity::class, parentColumns = ["id"], childColumns = ["fruitId"], onDelete = ForeignKey.CASCADE)
])

data class TaskEntity(


    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val nombreTarea: String,
    val descripcionTarea: String,
    val tipoActivid: TipoActividad,
    val fechaActividad : Date,
    var estadoActividad: Boolean,
    var tareaHecha: Boolean,
    val fruitId: Int

)