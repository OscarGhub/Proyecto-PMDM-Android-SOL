package com.example.fruitask.data

import com.example.fruitask.data.local.model.Fruit
import com.example.fruitask.data.local.entity.FruitEntity

//Convierte un objeto FruitEntity (BDD) a un objeto Fruit (Dominio/UI).
fun FruitEntity.toDomain(): Fruit {
    return Fruit(
        id = this.id,
        nombre = this.nombre,
        tipo = this.tipo,
        nivel = this.nivel,
        experiencia = this.experiencia
    )
}

//Convierte un objeto Fruit (Dominio/UI) a un objeto FruitEntity (BDD).
fun Fruit.toEntity(): FruitEntity {
    return FruitEntity(
        id = this.id,
        nombre = this.nombre,
        tipo = this.tipo,
        nivel = this.nivel,
        experiencia = this.experiencia
    )
}