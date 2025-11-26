package com.example.fruitask.data.local.repository

import com.example.fruitask.data.local.database.FruitDao
import com.example.fruitask.data.local.database.FruitEntity
import com.example.fruitask.data.model.Fruit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.fruitask.data.toDomain
import com.example.fruitask.data.toEntity

class FruitRepository(private val fruitDao: FruitDao) {

    // 1. OBTENER FRUTAS
    // Mapea el Flow de Entities a un Flow de objetos de Dominio (Fruit)
    fun getAllFruits(): Flow<List<Fruit>> {
        return fruitDao.getAllFruits().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    // 2. INSERTAR FRUTAS
    // Convierte el objeto de Dominio (Fruit) a Entity antes de pasarlo al DAO
    suspend fun insertarFruit(fruit: Fruit) {
        fruitDao.insertarFruit(fruit.toEntity())
    }

    suspend fun update(fruit: FruitEntity) {
        fruitDao.updateFruit(fruit)
    }

    suspend fun delete(fruitId: Int) {
        fruitDao.deleteFruitById(fruitId)
    }
}