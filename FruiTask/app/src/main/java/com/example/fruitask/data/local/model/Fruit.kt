package com.example.fruitask.data.local.model

data class Fruit(
    val id: Int = 0,
    val nombre: String,
    val tipo: FruitType,
    val isAvailable: Boolean = true
)