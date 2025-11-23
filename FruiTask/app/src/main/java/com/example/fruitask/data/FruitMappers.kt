package com.example.fruitask.data

import com.example.fruitask.data.local.database.FruitEntity
import com.example.fruitask.data.model.Fruit

//Convierte un objeto FruitEntity (BDD) a un objeto Fruit (Dominio/UI).

fun FruitEntity.toDomain(): Fruit {
    return Fruit(
        id = this.id,
        nombre = this.nombre,
        tipo = this.tipo
    )
}

//Convierte un objeto Fruit (Dominio/UI) a un objeto FruitEntity (BDD).

fun Fruit.toEntity(): FruitEntity {
    return FruitEntity(
        id = this.id,
        nombre = this.nombre,
        tipo = this.tipo
    )
}